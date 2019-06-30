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
import java.util.*;

public class Usuario {
	private final String nombre, mail;
	private Decision ultimaDecision = new DecisionVacia();
	private PrivilegiosUsuario privilegio = new Gratuito(10);
	private final Map<Evento, List<Sugerencia>> sugerenciasGeneradasParaEventos = new HashMap<>();
	private final List<Evento> eventosAgendados = new ArrayList<>();
	private final List<Sugerencia> historialSugerencias = new ArrayList<>();
	
	public Usuario(String nombre, String mail) {
		this.mail = mail;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
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

	public List<Evento> getEventosAgendados() {
		return eventosAgendados;
	}

	public Map<Evento, List<Sugerencia>> getSugerenciasGeneradasParaEventos() {
		return sugerenciasGeneradasParaEventos;
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
		if (eventosAgendados.contains(evento))
			throw new EventoYaFueAgendadoException();

		eventosAgendados.add(evento);

		Timer timer = new Timer();
		TimerTask generarSugerencias = new GenerarSugerencias(evento, guardarropaAUtilizar, sugerenciasGeneradasParaEventos, historialSugerencias);

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
}
