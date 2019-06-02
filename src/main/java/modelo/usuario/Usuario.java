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
import java.util.*;

public class Usuario {
	private final List<Guardarropa> guardarropas  = new ArrayList<>();
	private Decision ultimaDecision = new DecisionVacia();
	private PrivilegiosUsuario privilegio = new Gratuito(10);
	private final Map<Evento, List<Sugerencia>> sugerenciasGeneradasParaEventos = new HashMap<>();

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
		if(!tieneGuardarropa(guardarropa))
			throw new UsuarioNoEsDuenioDelGuardarropaException();

		privilegio.addPrenda(prendaNueva, guardarropa);
	}

	public void deshacerUltimaDecision() {
		ultimaDecision.deshacer();
		ultimaDecision = new DecisionVacia();
	}

	public void agendarEvento(Evento evento, Guardarropa guardarropaAUtilizar) {
		Timer timer = new Timer();
		TimerTask generarSugerencias = new GenerarSugerencias(evento, guardarropaAUtilizar, sugerenciasGeneradasParaEventos);
		Date fechaDeEjecucion = Timestamp.valueOf(evento.getFechaInicio().minusHours(2)); // Generar sugerencias dos horas antes del inicio del evento
		timer.schedule(generarSugerencias, fechaDeEjecucion);
	}

	public List<Sugerencia> obtenerSugerencias(Evento evento) throws EventoNoEstaProximoException, SinSugerenciasPosiblesException {
		if(!sugerenciasGeneradasParaEventos.containsKey(evento))
			throw new EventoNoEstaProximoException();

		List<Sugerencia> sugerencias = sugerenciasGeneradasParaEventos.get(evento);

		if(sugerencias.isEmpty())
			throw new SinSugerenciasPosiblesException();

		return sugerencias;
	}
}
