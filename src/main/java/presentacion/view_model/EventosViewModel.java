package presentacion.view_model;

//import javafx.collections.FXCollections;
//import javafx.collections.MapChangeListener;
//import javafx.collections.ObservableMap;
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
	private List<EventoObservable> eventosFiltrados;
	private int anioInicio = LocalDateTime.now().getYear();
	private int mesInicio = LocalDateTime.now().getMonthValue();
	private int diaInicio = LocalDateTime.now().getDayOfMonth();
	private int anioFin = LocalDateTime.now().plusDays(15).getYear();
	private int mesFin = LocalDateTime.now().plusDays(15).getMonthValue();
	private int diaFin = LocalDateTime.now().plusDays(15).getDayOfMonth();
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;

	public EventosViewModel(Usuario usuario) {
		inicializarFechas();
		this.usuario = usuario;
		hardcodearServicioDelClima();
		hardcodearUsuarioYEventos();
		inicializarEventos();
		eventosFiltrados = eventos;
		//observarGeneracionDeSugerencias();
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

		Evento evento1 = new Evento("Go shopping", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
		Evento evento2 = new Evento("Running", LocalDateTime.now().plusHours(2).plusSeconds(8), LocalDateTime.now().plusHours(3), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
		Evento evento3 = new Evento("Go to University", LocalDateTime.now().plusDays(24).plusHours(2).plusSeconds(12), LocalDateTime.now().plusDays(24).plusHours(3), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
		Evento evento4 = new Evento("Go to work", LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(10).plusHours(8), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);

		usuario.agendarEvento(evento1, guardarropa);
		usuario.agendarEvento(evento2, guardarropa);
		usuario.agendarEvento(evento3, guardarropa);
		usuario.agendarEvento(evento4, guardarropa);
	}
	
	private void inicializarFechas() {
		fechaInicio = LocalDateTime.of(anioInicio, mesInicio, diaInicio, 0, 0);
		fechaFin = LocalDateTime.of(anioFin, mesFin, diaFin, 0, 0);
	}
	
	private void inicializarEventos() {
		this.setearEventosIniciales(usuario.getEventos().stream().map(
				evento -> evento.getSugerencias().isEmpty() ?
						new EventoObservable(evento, false) : new EventoObservable(evento, true)
		).collect(Collectors.toList()));
	}

	/*private void observarGeneracionDeSugerencias() {
		ObservableMap<Evento, List<Sugerencia>> eventosListaObs = FXCollections.observableMap(usuario.getSugerenciasParaEventos());
		MapChangeListener<Evento, List<Sugerencia>> listener = change -> inicializarEventos();
		eventosListaObs.addListener(listener);
	}*/

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}
	
	public void setFechaInicio(LocalDateTime fi) {
		fechaInicio = fi;
	}

	public void setFechaFin(LocalDateTime ff) {
		fechaFin = ff;
	}

	public int getAnioInicio() {
		return anioInicio;
	}

	public void setAnioInicio(int anioInicio) {
		this.anioInicio = anioInicio;
	}

	public int getMesInicio() {
		return mesInicio;
	}

	public void setMesInicio(int mesInicio) {
		if(mesInicio > 0 && mesInicio < 13) 
			this.mesInicio = mesInicio;
	}

	public int getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(int diaInicio) {
		if(cumpleCondicionDeDiaDeMes(diaInicio, fechaInicio.getMonthValue()))
			this.diaInicio = diaInicio;
	}

	public int getAnioFin() {
		return anioFin;
	}

	public void setAnioFin(int anioFin) {
		this.anioFin = anioFin;
	}

	public int getMesFin() {
		return mesFin;
	}

	public void setMesFin(int mesFin) {
		if(mesFin > 0 && mesFin < 13) 
			this.mesFin = mesFin;
	}

	public int getDiaFin() {
		return diaFin;
	}

	public void setDiaFin(int diaFin) {
		if(cumpleCondicionDeDiaDeMes(diaFin, fechaFin.getMonthValue())) 
			this.diaFin = diaFin;
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
		meses30.add(4);
		meses30.add(6);
		meses30.add(9);
		meses30.add(11);
		ArrayList<Integer> meses29 = new ArrayList<Integer>();
		meses29.add(2);
		retorno = meses31.contains(mes) && dia <= 31 && dia > 0;
		if(!retorno)
			retorno = meses30.contains(mes) && dia <= 30 && dia > 0;
		if(!retorno)
			retorno = meses29.contains(mes) && dia <= 29 && dia > 0;

		return retorno;
	}

	public List<EventoObservable> getEventosFiltrados() {
		return eventosFiltrados;
	}

	public void setEventosFiltrados(List<EventoObservable> eventosFiltrados) {
		this.eventosFiltrados = eventosFiltrados;
	}
}
