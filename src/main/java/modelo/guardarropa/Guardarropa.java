package modelo.guardarropa;

import com.google.common.collect.Lists;
import excepciones.PronosticoNoDisponibleException;
import excepciones.SinSugerenciasPosiblesException;
import modelo.atuendo.Atuendo;
import modelo.evento.Evento;
import modelo.prenda.Categoria;
import modelo.prenda.Prenda;
import modelo.prenda.PrototipoSuperposicion;
import modelo.prenda.Tipo;
import modelo.pronosticos_del_clima.Pronostico;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.pronosticos_del_clima.clima.Clima;
import modelo.sugerencia.EstadoSugerencia;
import modelo.sugerencia.SensibilidadParteDelCuerpo;
import modelo.sugerencia.SensibilidadTemperatura;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "guardarropas")
public class Guardarropa {
	@Id
	@GeneratedValue
	private long Id;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="guardarropa_id")
	private final List<Prenda> prendas = new ArrayList<>();
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		joinColumns = { @JoinColumn(name = "guardarropa_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "usuario_id") }
	)
	private final List<Usuario> usuariosPropietarios = new ArrayList<>();

	public Guardarropa(){}

	
	public long getId() {
		return Id;
	}

	public List<Usuario> getUsuariosPropietarios() {
		return usuariosPropietarios;
	}

	public List<Prenda> getPrendasSuperiores() {
		return getPrendasDeCategoria(Categoria.SUPERIOR);
	}

	public List<Prenda> getPrendasInferiores() {
		return getPrendasDeCategoria(Categoria.INFERIOR);
	}

	public List<Prenda> getCalzados() {
		return getPrendasDeCategoria(Categoria.CALZADO);
	}

	public List<Prenda> getAccesorios() {
		return getPrendasDeCategoria(Categoria.ACCESORIO);
	}

	public List<Prenda> getPrendasDeCategoria(Categoria cat) {
		return prendas.stream().filter(p -> p.getCategoria() == cat).collect(Collectors.toList());
	}

	public int cantidadPrendas() {
		return this.prendas.size();
	}

	public void addPrenda(Prenda prenda) {
		this.prendas.add(prenda);
	}

	public void removePrenda(Prenda prenda) {
		this.prendas.remove(prenda);
	}

	public boolean tienePrenda(Prenda prenda) {
		return this.prendas.contains(prenda);
	}

	public void addUsuario(Usuario usuario) {
		this.usuariosPropietarios.add(usuario);
	}

	public void removeUsuario(Usuario usuario) {
		this.usuariosPropietarios.remove(usuario);
	}

	public boolean tieneUsuario(Usuario usuario) {
		return this.usuariosPropietarios.contains(usuario);
	}

	public List<Prenda> getPrendas() {
		return this.prendas;
	}
	
	public List<Sugerencia> generarSugerencias(Evento evento, List<Sugerencia> historialSugerencias)
			throws PronosticoNoDisponibleException, SinSugerenciasPosiblesException {

		Pronostico pronostico = 
				ServicioDelClima
				.getInstance()
				.obtenerPronosticoPromedioEntre2Fechas(evento.getFechaInicio(), evento.getFechaFin());
		
		Clima clima = pronostico.getClima();

		
		List<List<Prenda>> conjuntosDePrendasSuperioresPosibles = 
				conjuntoDePrendasDeCiertaCategoríaDisponiblesDadoElClimaEHistorialDeSugerencias(Categoria.SUPERIOR, clima, historialSugerencias);
		
		List<List<Prenda>> conjuntosDePrendasInferioresPosibles =
				conjuntoDePrendasDeCiertaCategoríaDisponiblesDadoElClimaEHistorialDeSugerencias(Categoria.INFERIOR, clima, historialSugerencias);
		
		List<List<Prenda>> conjuntosDeCalzadosPosibles =
				conjuntoDePrendasDeCiertaCategoríaDisponiblesDadoElClimaEHistorialDeSugerencias(Categoria.CALZADO, clima, historialSugerencias);
		
		List<List<Prenda>> conjuntosDeAccesoriosPosibles = 
				conjuntoDePrendasDeCiertaCategoríaDisponiblesDadoElClimaEHistorialDeSugerencias(Categoria.ACCESORIO, clima, historialSugerencias);
		
		
		List<Sugerencia> ret = 
				ObtenerListaDeSugerenciasDefinitiva(conjuntosDePrendasSuperioresPosibles,
													conjuntosDePrendasInferioresPosibles,
													conjuntosDeCalzadosPosibles,
													conjuntosDeAccesoriosPosibles);

		if (ret.isEmpty())
			throw new SinSugerenciasPosiblesException();

		return ret;
	}
	
	private List<List<Prenda>> conjuntoDePrendasDeCiertaCategoríaDisponiblesDadoElClimaEHistorialDeSugerencias(Categoria categoria, Clima clima, List<Sugerencia> historialSugerencias) {
		List<PrototipoSuperposicion> superposicionesPosibles = 
				obtenerSuperposicionesDeTiposDeCategoriaPorClima(categoria, clima, obtenerSensibilidadGlobal(historialSugerencias), sencibilidadPorParteDelCuerpo(historialSugerencias));
		
		return obtenerPrendasQueSatisfacenPrototipo(categoria, superposicionesPosibles);
	}
	
	private List<SensibilidadParteDelCuerpo> sencibilidadPorParteDelCuerpo(List<Sugerencia> historialSugerencias){
		return historialSugerencias
				.stream()
				.filter(sug -> sug.getCalificacion() != null && sug.getCalificacion().getSensibilidadPorPartesDelCuerpo() != null)
				.map(sug -> sug.getCalificacion().getSensibilidadPorPartesDelCuerpo())
				.flatMap(List::stream)
		        .collect(Collectors.toList());
	}

	private List<PrototipoSuperposicion> obtenerSuperposicionesDeTiposDeCategoriaPorClima(Categoria categoria,
			Clima clima, SensibilidadTemperatura sensibilidadGlobal,
			List<SensibilidadParteDelCuerpo> sensibilidadPorParteDelCuerpo) {
		
		double modificadorCelsiusSegunSensibilidadGlobal = 
				sensibilidadGlobal.getModificadorCelcius();
				
		double celsius = clima.getTemperatura().toCelsius().getValor() + modificadorCelsiusSegunSensibilidadGlobal;

		return Tipo
				.obtenerPrototiposSuperposiciones(categoria)
				.stream()
				.filter(superposicion -> superposicion.getTipos().stream().allMatch(tipo -> {
					SensibilidadTemperatura sensibilidadEnEsaParte = 
							SensibilidadTemperatura
							.obtenerPromedioDeSensibilidad(sensibilidadPorParteDelCuerpo
									.stream()
									.filter(sens -> sens.getParteDelCuerpo() == tipo.getParteDelCuerpo())
									.map(sens -> sens.getSensibilidad())
									.collect(Collectors.toList()));
					
					double modificadorCelsius = sensibilidadEnEsaParte.getModificadorCelcius();

					return superposicion.getTemperaturaMinima().getValor() <= celsius + modificadorCelsius
							&& superposicion.getTemperaturaMaxima().getValor() >= celsius + modificadorCelsius;
							
				})).collect(Collectors.toList());
	}

	private List<List<Prenda>> obtenerPrendasQueSatisfacenPrototipo(Categoria categoria, List<PrototipoSuperposicion> prototipos) {
		
		List<Prenda> prendasDeCategoria = this.getPrendasDeCategoria(categoria);

		List<List<Tipo>> todosLosTipos = 
				prototipos
				.stream()
				.map(PrototipoSuperposicion::getTipos)
				.collect(Collectors.toList());

		return todosLosTipos
				.stream()
				.flatMap(tipos -> {
					
					List<List<Prenda>> prendas = 
							tipos
							.stream().map(tipo -> prendasDeCategoria.stream()
									.filter(prenda -> prenda.getTipo() == tipo)
									.collect(Collectors.toList()))
							.collect(Collectors.toList());
					
					List<List<Prenda>> prendasCartesiano = Lists.cartesianProduct(prendas);
					
					return prendasCartesiano.stream();
			
				}).collect(Collectors.toList());
	}

	private SensibilidadTemperatura obtenerSensibilidadGlobal(List<Sugerencia> historialSugerencias) {
		
		SensibilidadTemperatura sensibilidadGlobal = SensibilidadTemperatura.NORMAL;

		if(!historialSugerencias.isEmpty()) {
			sensibilidadGlobal = 
					SensibilidadTemperatura
					.obtenerPromedioDeSensibilidad(
							historialSugerencias
							.stream()
							.filter(sug -> sug.getCalificacion() != null)
							.map(sug -> sug.getCalificacion().getSensibilidadGlobal())
							.collect(Collectors.toList())
					);
		}

		return sensibilidadGlobal;
	}

	private List<Sugerencia> ObtenerListaDeSugerenciasDefinitiva(List<List<Prenda>> prendasSuperiores, List<List<Prenda>> prendasInferiores, List<List<Prenda>> calzados, List<List<Prenda>> accesorios) {
		List<Sugerencia> ret = 
				Lists
				.cartesianProduct(prendasSuperiores, prendasInferiores, calzados, accesorios)
				.stream()
				.map(result -> new Sugerencia(
						new Atuendo(result.get(0), result.get(1).get(0), result.get(2).get(0), result.get(3))))
				.filter(sug -> sinPrendasEnUsoPorOtroUsuario(sug)).collect(Collectors.toList());
		
		return ret;
	}
	
	private boolean prendaEnUso(Prenda prenda) {
		return this.usuariosPropietarios
				.stream()
				.anyMatch(user -> user
								.getHistorialSugerencias()
								.stream()
								.anyMatch(sug -> sug.getEstado() == EstadoSugerencia.ACEPTADO
												&& sug.getAtuendo().obtenerTodasLasPrendas().contains(prenda)
										 )
						 );
	}
	
	private boolean sinPrendasEnUsoPorOtroUsuario(Sugerencia sugerencia) {
		return sugerencia
				.getAtuendo()
				.obtenerTodasLasPrendas()
				.stream()
				.noneMatch(prenda -> prendaEnUso(prenda));
	}
}
