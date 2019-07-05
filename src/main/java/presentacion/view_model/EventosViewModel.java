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
import org.uqbar.lacar.ui.model.Action;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Observable
public class EventosViewModel {
	private final Usuario usuario;
	private List<EventoObservable> eventos;
	private int anioInicio = LocalDateTime.now().getYear();
	private int mesInicio = LocalDateTime.now().getMonthValue();
	private int diaInicio = LocalDateTime.now().getDayOfMonth();
	private int anioFin = LocalDateTime.now().plusDays(1).getYear();
	private int mesFin = LocalDateTime.now().plusDays(1).getMonthValue();
	private int diaFin = LocalDateTime.now().plusDays(1).getDayOfMonth();
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;

	public EventosViewModel(Usuario usuario) {
		inicializarFechas();
		this.usuario = usuario;
		hardcodearServicioDelClima();
		hardcodearUsuarioYEventos();
		inicializarEventos();
		observarGeneracionDeSugerencias();
	}

	public List<EventoObservable> getEventos() {
		return eventos;
	}
	
	public void setEventos(List<EventoObservable> ev) {
		eventos = ev;
	}

	public void setearEventosIniciales(List<EventoObservable> eventos) {
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
	
	private void inicializarFechas() {
		fechaInicio = LocalDateTime.of(anioInicio, mesInicio, diaInicio, 0, 0);
		fechaFin = LocalDateTime.of(anioFin, mesFin, diaFin, 0, 0);
	}
	
	private void inicializarEventos() {
		this.setearEventosIniciales(usuario.getSugerenciasParaEventos().keySet().stream().map(
				evento -> usuario.getSugerenciasParaEventos().getOrDefault(evento, new ArrayList<>()).isEmpty() ?
						new EventoObservable(evento, false) : new EventoObservable(evento, true)
		).collect(Collectors.toList()));
	}

	private void observarGeneracionDeSugerencias() {
		ObservableMap<Evento, List<Sugerencia>> eventosListaObs = FXCollections.observableMap(usuario.getSugerenciasParaEventos());
		MapChangeListener<Evento, List<Sugerencia>> listener = change -> inicializarEventos();
		eventosListaObs.addListener(listener);
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public int getAnioInicio() {
		return anioInicio;
	}

	public void setAnioInicio(int anioInicio) {
		fechaInicio = LocalDateTime.of(anioInicio, fechaInicio.getMonthValue(), fechaInicio.getDayOfMonth(), 0, 0);
		this.anioInicio = anioInicio;
	}

	public int getMesInicio() {
		return mesInicio;
	}

	public void setMesInicio(int mesInicio) {
		if(mesInicio > 0 && mesInicio < 13) {
			fechaInicio = LocalDateTime.of(fechaInicio.getYear(), mesInicio, fechaInicio.getDayOfMonth(), 0, 0);
			this.mesInicio = mesInicio;
		}
	}

	public int getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(int diaInicio) {
		if(cumpleCondicionDeDiaDeMes(diaInicio, fechaInicio.getMonthValue())) {
			fechaInicio = LocalDateTime.of(fechaInicio.getYear(), fechaInicio.getMonthValue(), diaInicio, 0, 0);
			this.diaInicio = diaInicio;
		}
	}

	public int getAnioFin() {
		return anioFin;
	}

	public void setAnioFin(int anioFin) {
		fechaFin = LocalDateTime.of(anioFin, fechaFin.getMonthValue(), fechaFin.getDayOfMonth(), 0, 0);
		this.anioFin = anioFin;
	}

	public int getMesFin() {
		return mesFin;
	}

	public void setMesFin(int mesFin) {
		if(mesFin > 0 && mesFin < 13) {
			fechaFin = LocalDateTime.of(fechaFin.getYear(), mesFin, fechaFin.getDayOfMonth(), 0, 0);
			this.mesFin = mesFin;
		}
	}

	public int getDiaFin() {
		return diaFin;
	}

	public void setDiaFin(int diaFin) {
		if(cumpleCondicionDeDiaDeMes(diaFin, fechaFin.getMonthValue())) {
			fechaFin = LocalDateTime.of(fechaFin.getYear(), fechaFin.getMonthValue(), diaFin, 0, 0);
			this.diaFin = diaFin;
		}
	}
	
	private boolean cumpleCondicionDeDiaDeMes(int dia, int mes) {
		boolean retorno;
		ArrayList<Integer> meses31 = new ArrayList<Integer>();
		meses31.add(1);
		meses31.add(3);
		meses31.add(5);
		meses31.add(7);
		meses31.add(8);
		meses31.add(10);
		meses31.add(12);
		ArrayList<Integer> meses30 = new ArrayList<Integer>();
		meses31.add(4);
		meses31.add(6);
		meses31.add(9);
		meses31.add(11);
		ArrayList<Integer> meses29 = new ArrayList<Integer>();
		meses31.add(2);
		retorno = meses31.contains(mes) && dia <= 31 && dia < 0;
		if(!retorno)
			retorno = meses30.contains(mes) && dia <= 30 && dia < 0;
		if(!retorno)
			retorno = meses29.contains(mes) && dia <= 29 && dia < 0;

		return retorno;
	}
}
