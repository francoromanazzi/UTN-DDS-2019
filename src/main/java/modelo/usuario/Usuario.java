package modelo.usuario;

import excepciones.*;
import cron_jobs.GenerarSugerencias;
import modelo.alerta_meteorologica.accion_ante_alerta_meteorologica.AccionAnteAlertaMeteorologica;
import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.evento.Evento;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;
import modelo.sugerencia.Sugerencia;
import modelo.sugerencia.decision.Decision;
import modelo.sugerencia.decision.DecisionVacia;

import javax.mail.MessagingException;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Entity
@Table(name="usuarios")
public class Usuario {
	@Id @GeneratedValue
	private Long Id;
	private String nombre, mail, numeroTelefono;
	@Transient //Temporal?
	private Decision ultimaDecision = new DecisionVacia();
	@Transient //Persistirla dsp de resolver la herencia en PrivilegiosUsuario
	private PrivilegiosUsuario privilegio = new Gratuito(10);
	@OneToMany
	@JoinColumn(name="usuario_id")
	private final List<Evento> eventos = new ArrayList<>();
	@OneToMany
	@JoinColumn(name="usuario_id") // Agregar @OrderColumn
	private final List<Sugerencia> historialSugerencias = new ArrayList<>();
	@Transient //Persistir?
	private final List<AccionAnteAlertaMeteorologica> accionesAnteAlertaMeteorologica = new ArrayList<>();
	
	public Usuario() {}

	public Usuario(String nombre, String mail, String nro) {
		this.mail = mail;
		this.nombre = nombre;
		this.numeroTelefono = nro;
	}

	public Long getId() {
		return this.Id;
	}
	
	public List<Evento> getEventos() {
		return eventos;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getNumeroTelefono() {
		return numeroTelefono;
	}
	
	public String getMail() {
		return mail;
	}

	public Decision getUltimaDecision() {
		return ultimaDecision;
	}

	public void setUltimaDecision(Decision ultimaDecision) {
		this.ultimaDecision = ultimaDecision;
	}

	public PrivilegiosUsuario getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(PrivilegiosUsuario privilegio) {
		this.privilegio = privilegio;
	}

	public List<Sugerencia> getHistorialSugerencias() {
		return historialSugerencias;
	}

	public void addToHistorialSugerencias(List<Sugerencia> s) {
		historialSugerencias.addAll(s);
	}
	
	public void addGuardarropa(Guardarropa guardarropa) throws GuardarropaConMayorPrendasQueCapMaxException {
		privilegio.addGuardarropa(guardarropa, this);
	}

	public void removeGuardarropa(Guardarropa guardarropa) {
		guardarropa.removeUsuario(this);
	}

	public boolean tieneGuardarropa(Guardarropa guardarropa) {
		return guardarropa.tieneUsuario(this);
	}

	public void addPrenda(Prenda prendaNueva, Guardarropa guardarropa) throws UsuarioNoEsPropietarioDelGuardarropaException, CapacidadExcedidaGuardarropaException {
		if (!tieneGuardarropa(guardarropa))
			throw new UsuarioNoEsPropietarioDelGuardarropaException();

		privilegio.addPrenda(prendaNueva, guardarropa);
	}

	public void deshacerUltimaDecision() {
		ultimaDecision.deshacer();
		ultimaDecision = new DecisionVacia();
	}

	public void agendarEvento(Evento evento, Guardarropa guardarropaAUtilizar) throws EventoYaFueAgendadoException, UsuarioNoEsPropietarioDelGuardarropaException {
		if (eventos.contains(evento))
			throw new EventoYaFueAgendadoException();
		
		eventos.add(evento);

		Timer timer = new Timer();
		TimerTask generarSugerencias = new GenerarSugerencias(evento, guardarropaAUtilizar, this.historialSugerencias, this);

		LocalDateTime fechaDeEjecucion = evento.getFechaInicio().minusHours(2);
		
		if (!fechaDeEjecucion.isAfter(LocalDateTime.now())) 
			timer.schedule(generarSugerencias, 0, evento.getFrecuencia().getPerido());
		else {
			long delay = LocalDateTime.now().until(fechaDeEjecucion, ChronoUnit.MILLIS);
			timer.schedule(generarSugerencias, delay, evento.getFrecuencia().getPerido());
		}	
	}

	public void eliminarEvento(Evento e) {
		eventos.remove(e);
	}
	
	public List<Sugerencia> obtenerSugerencias(Evento evento) throws EventoNoFueAgendadoException, EventoNoEstaProximoException, SinSugerenciasPosiblesException, PronosticoNoDisponibleException {
		if (!eventos.contains(evento))
			throw new EventoNoFueAgendadoException();
		if (evento.getSugerencias().isEmpty())
			throw new EventoNoEstaProximoException();

		return evento.getSugerencias();
	}

	public void agregarAccionAnteAlertaMeteorologica(AccionAnteAlertaMeteorologica accion) {
		accionesAnteAlertaMeteorologica.add(accion);
	}

	public void quitarAccionAnteAlertaMeteorologica(AccionAnteAlertaMeteorologica accion) {
		accionesAnteAlertaMeteorologica.remove(accion);
	}

	public void recibirAlertaMeteorologica(AlertaMeteorologica alerta) {
		accionesAnteAlertaMeteorologica.forEach(accion -> {
			try {
				if(alerta == AlertaMeteorologica.LLUVIA) {
					accion.anteLluvia(this);
				}
				else {
					accion.anteGranizo(this);
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		});
	}
	
	// Usamos esto ?
	public void controlarTamanioHistorialSugerencias() {
		int cantidadMaxima = 50;
		int cantidadDeSugerencias = this.historialSugerencias.size();
		
		if(cantidadDeSugerencias > cantidadMaxima) {
			int excedente = cantidadMaxima - cantidadDeSugerencias;
			this.historialSugerencias.subList(0, excedente).clear(); // Saco el excedente de sugerencias.
		}
			
	}
}
