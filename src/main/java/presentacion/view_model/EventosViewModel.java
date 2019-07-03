package presentacion.view_model;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import modelo.evento.TipoEvento;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import modelo.pronosticos_del_clima.Pronostico;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.pronosticos_del_clima.clima.Clima;
import modelo.pronosticos_del_clima.clima.temperatura.Celsius;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Premium;
import modelo.usuario.Usuario;
import org.uqbar.commons.model.annotations.Observable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Observable
public class EventosViewModel {
	private final Usuario usuario;
	private List<EventoObservable> eventos;

	public EventosViewModel(Usuario usuario) {
		this.usuario = usuario;
		hardcodearServicioDelClima();
		hardcodearUsuarioYEventos();
		inicializarEventos();
		observarGeneracionDeSugerencias();
	}

	public List<EventoObservable> getEventos() {
		return eventos;
	}

	public void setEventos(List<EventoObservable> eventos) {
		this.eventos = eventos.stream().sorted(
				Comparator.comparing(e -> e.getEvento().getFechaInicio())
		).collect(Collectors.toList());
	}

	private void hardcodearServicioDelClima() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();
		List<Pronostico> pronosticos = new ArrayList<>(Arrays.asList(
				new Pronostico(LocalDateTime.now().minusHours(6), LocalDateTime.now().plusHours(6), new Clima(new Celsius(28.5)))
		));
		servicioDelClima.setPronosticosCache(pronosticos);
	}

	private void hardcodearUsuarioYEventos() {
		usuario.setPrivilegio(new Premium());

		Guardarropa guardarropa = new Guardarropa();
		usuario.addGuardarropa(guardarropa);

		usuario.addPrenda(new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.empty()), guardarropa);
		usuario.addPrenda(new Prenda(Tipo.PANTALON_CORTO, Material.DENIM, new Color(0, 0, 0), Optional.empty(), Optional.empty()), guardarropa);
		usuario.addPrenda(new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(0, 0, 0), Optional.empty(), Optional.empty()), guardarropa);

		Evento evento1 = new Evento("Ir a comprar", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
		Evento evento2 = new Evento("Salir a correr", LocalDateTime.now().plusHours(2).plusSeconds(8), LocalDateTime.now().plusHours(3), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
		Evento evento3 = new Evento("Ir a la facultad", LocalDateTime.now().plusHours(2).plusSeconds(12), LocalDateTime.now().plusHours(3), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);

		usuario.agendarEvento(evento1, guardarropa);
		usuario.agendarEvento(evento2, guardarropa);
		usuario.agendarEvento(evento3, guardarropa);
	}

	private void inicializarEventos() {
		this.setEventos(usuario.getSugerenciasParaEventos().keySet().stream().map(
				evento -> usuario.getSugerenciasParaEventos().getOrDefault(evento, new ArrayList<>()).isEmpty() ?
						new EventoObservable(evento, false) : new EventoObservable(evento, true)
		).collect(Collectors.toList()));
	}

	private void observarGeneracionDeSugerencias() {
		ObservableMap<Evento, List<Sugerencia>> eventosListaObs = FXCollections.observableMap(usuario.getSugerenciasParaEventos());
		MapChangeListener<Evento, List<Sugerencia>> listener = change -> inicializarEventos();
		eventosListaObs.addListener(listener);
	}
}
