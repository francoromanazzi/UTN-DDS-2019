package modelo.usuario;

import excepciones.*;
import modelo.cron_jobs.GenerarSugerencias;
import modelo.evento.Evento;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;
import modelo.sugerencia.Sugerencia;
import modelo.sugerencia.decision.Decision;
import modelo.sugerencia.decision.DecisionVacia;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class Usuario {
	private String nombre;
	private String mail;
	private final List<Guardarropa> guardarropas = new ArrayList<>();
	private Decision ultimaDecision = new DecisionVacia();
	private PrivilegiosUsuario privilegio = new Gratuito(10);
	private final Map<Evento, List<Sugerencia>> sugerenciasGeneradasParaEventos = new HashMap<>();
	private final List<Evento> eventosAgendados = new ArrayList<>();
	
	public Usuario(String nombre, String mail) {
		this.mail = mail;
		this.nombre = nombre;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public List<Guardarropa> getGuardarropas() {
		return guardarropas;
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

	public void addGuardarropa(Guardarropa guardarropa) throws GuardarropaConMayorPrendasQueCapMaxException {
		privilegio.addGuardarropa(this.guardarropas, guardarropa);
	}

	public void removeGuardarropa(Guardarropa guardarropa) {
		this.guardarropas.remove(guardarropa);
	}

	public boolean tieneGuardarropa(Guardarropa g) {
		return this.guardarropas.contains(g);
	}

	public void addPrenda(Prenda prendaNueva, Guardarropa guardarropa) throws UsuarioNoEsDuenioDelGuardarropaException, CapacidadExcedidaGuardarropaException {
		if (!tieneGuardarropa(guardarropa))
			throw new UsuarioNoEsDuenioDelGuardarropaException();

		privilegio.addPrenda(prendaNueva, guardarropa);
	}

	public void deshacerUltimaDecision() {
		ultimaDecision.deshacer();
		ultimaDecision = new DecisionVacia();
	}

	public void agendarEvento(Evento evento, Guardarropa guardarropaAUtilizar) throws EventoYaFueAgendadoException {
		if (eventosAgendados.contains(evento))
			throw new EventoYaFueAgendadoException();

		eventosAgendados.add(evento);

		Timer timer = new Timer();
		TimerTask generarSugerencias = new GenerarSugerencias(evento, guardarropaAUtilizar, sugerenciasGeneradasParaEventos);

		LocalDateTime fechaDeEjecucion = evento.getFechaInicio().minusHours(2);

		if (!fechaDeEjecucion.isAfter(LocalDateTime.now())) {
			timer.schedule(generarSugerencias, 0);
		} else {
			timer.schedule(generarSugerencias, Timestamp.valueOf(fechaDeEjecucion));
		}
	}

	public List<Sugerencia> obtenerSugerencias(Evento evento) throws EventoNoFueAgendadoException, EventoNoEstaProximoException, SinSugerenciasPosiblesException, PronosticoNoDisponibleException {
		if (!eventosAgendados.contains(evento))
			throw new EventoNoFueAgendadoException();
		if (!sugerenciasGeneradasParaEventos.containsKey(evento))
			throw new EventoNoEstaProximoException();

		return sugerenciasGeneradasParaEventos.get(evento);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
