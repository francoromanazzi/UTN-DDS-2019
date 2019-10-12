package modelo.usuario;

import cron_jobs.GenerarSugerencias;
import excepciones.*;
import mocks.GenerarSugerenciasNoPegarleALaDBNiNotificar;
import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.alerta_meteorologica.accion_ante_alerta_meteorologica.AccionAnteAlertaMeteorologica;
import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;
import modelo.sugerencia.Sugerencia;
import modelo.sugerencia.decision.Decision;
import modelo.sugerencia.decision.DecisionVacia;
import utils.SHA256Builder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue
	private Long Id;
	private String nombre, mail, numeroTelefono, username, password;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Decision ultimaDecision = new DecisionVacia();
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private PrivilegioUsuario privilegio = new Gratuito(10);
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	private final List<Evento> eventos = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	private final List<Sugerencia> historialSugerencias = new ArrayList<>(); // Para la generacion de sugerencias en los guardarropas compartidos
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "accion_ante_alerta_meteorologica_id")
	private final List<AccionAnteAlertaMeteorologica> accionesAnteAlertaMeteorologica = new ArrayList<>();

	public Usuario() {
	}

	public Usuario(String nombre, String mail, String nro, String username, String password) {
		this.mail = mail;
		this.nombre = nombre;
		this.numeroTelefono = nro;
		this.username = username;
		this.password = SHA256Builder.generarHash(password);
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

	public PrivilegioUsuario getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(PrivilegioUsuario privilegio) {
		this.privilegio = privilegio;
	}

	public List<Sugerencia> getHistorialSugerencias() {
		return historialSugerencias;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

		planificarGeneracionSugerencias(evento, new GenerarSugerencias(evento.getId(), guardarropaAUtilizar.getId(), this.Id));
	}

	public void agendarEventoMockNoGenerarSugerencias(Evento evento, Guardarropa guardarropaAUtilizar) {
		if (eventos.contains(evento))
			throw new EventoYaFueAgendadoException();

		eventos.add(evento);
	}

	public void agendarEventoMockNoPegarleALaDBNiNotificar(Evento evento, Guardarropa guardarropaAUtilizar) {
		if (eventos.contains(evento))
			throw new EventoYaFueAgendadoException();

		eventos.add(evento);

		planificarGeneracionSugerencias(evento, new GenerarSugerenciasNoPegarleALaDBNiNotificar(evento, guardarropaAUtilizar, this));
	}

	private void planificarGeneracionSugerencias(Evento evento, TimerTask generarSugerencias) {
		Timer timer = new Timer();
		LocalDateTime fechaDeEjecucion = evento.getFechaInicio().minusHours(2);

		long delay = !fechaDeEjecucion.isAfter(LocalDateTime.now()) ? 0 : LocalDateTime.now().until(fechaDeEjecucion, ChronoUnit.MILLIS);

		if(evento.getFrecuencia() == FrecuenciaEvento.UNICA_VEZ)
			timer.schedule(generarSugerencias, delay);
		else
			timer.schedule(generarSugerencias, delay, evento.getFrecuencia().getPerido());
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

	public void recibirAlertaMeteorologica(AlertaMeteorologica alerta) throws MensajeriaException {
		for (AccionAnteAlertaMeteorologica accion : accionesAnteAlertaMeteorologica) {
			if (alerta == AlertaMeteorologica.LLUVIA)
				accion.anteLluvia(this);
			if (alerta == AlertaMeteorologica.NIEVE)
				accion.anteNieve(this);
			else if (alerta == AlertaMeteorologica.GRANIZO)
				accion.anteGranizo(this);
		}
	}
}
