package modelo.usuario;

import excepciones.*;
import cron_jobs.GenerarSugerencias;
import modelo.evento.Evento;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;
import modelo.sugerencia.Sugerencia;
import modelo.sugerencia.decision.Decision;
import modelo.sugerencia.decision.DecisionVacia;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Usuario {
	private final String nombre, mail, numeroTelefono;
	private Decision ultimaDecision = new DecisionVacia();
	private PrivilegiosUsuario privilegio = new Gratuito(10);
	private final Map<Evento, List<Sugerencia>> sugerenciasParaEventos = new HashMap<>();
	private final List<Sugerencia> historialSugerencias = new ArrayList<>();
	
	public Usuario(String nombre, String mail, String nro) {
		this.mail = mail;
		this.nombre = nombre;
		this.numeroTelefono = nro;
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

	public Map<Evento, List<Sugerencia>> getSugerenciasParaEventos() {
		return sugerenciasParaEventos;
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
		if (sugerenciasParaEventos.containsKey(evento))
			throw new EventoYaFueAgendadoException();

		sugerenciasParaEventos.put(evento, new ArrayList<>());

		Timer timer = new Timer();
		TimerTask generarSugerencias = new GenerarSugerencias(evento, guardarropaAUtilizar, sugerenciasParaEventos, historialSugerencias, this);

		LocalDateTime fechaDeEjecucion = evento.getFechaInicio().minusHours(2);
		
		if (!fechaDeEjecucion.isAfter(LocalDateTime.now())) 
			timer.schedule(generarSugerencias, 0, evento.getFrecuencia().getPerido());
		else {
			long delay = LocalDateTime.now().until(fechaDeEjecucion, ChronoUnit.MILLIS);
			timer.schedule(generarSugerencias, delay, evento.getFrecuencia().getPerido());
		}	
	}

	public List<Sugerencia> obtenerSugerencias(Evento evento) throws EventoNoFueAgendadoException, EventoNoEstaProximoException, SinSugerenciasPosiblesException, PronosticoNoDisponibleException {
		if (!sugerenciasParaEventos.containsKey(evento))
			throw new EventoNoFueAgendadoException();
		if (sugerenciasParaEventos.get(evento).isEmpty())
			throw new EventoNoEstaProximoException();

		return sugerenciasParaEventos.get(evento);
	}
}
