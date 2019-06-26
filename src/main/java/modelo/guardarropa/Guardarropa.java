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
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Guardarropa {
	private final List<Prenda> prendas = new ArrayList<>();
	private final List<Usuario> usuariosPropietarios = new ArrayList<>();

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

	public List<Sugerencia> generarSugerencias(Evento evento, List<Sugerencia> historialSugerencias) throws PronosticoNoDisponibleException, SinSugerenciasPosiblesException {
		Predicate<Prenda> prendaEnUso = prenda ->
				usuariosPropietarios.stream().anyMatch(user ->
						user.getHistorialSugerencias().stream().anyMatch(sug ->
								sug.getEstado() == EstadoSugerencia.ACEPTADO && sug.getAtuendo().obtenerTodasLasPrendas().contains(prenda)
						)
				);

		Predicate<Sugerencia> sinPrendasEnUsoPorOtroUsuario = sugerencia ->
				sugerencia.getAtuendo().obtenerTodasLasPrendas().stream().noneMatch(prendaEnUso);

		Pronostico pronostico = ServicioDelClima.getInstance().obtenerPronosticoPromedioEntre2Fechas(evento.getFechaInicio(), evento.getFechaFin());
		Clima clima = pronostico.getClima();

		List<PrototipoSuperposicion> superposicionesTiposDeSuperiores = obtenerSuperposicionesDeTiposDeCategoriaPorClima(Categoria.SUPERIOR, clima);
		List<PrototipoSuperposicion> superposicionesTiposDeInferiores = obtenerSuperposicionesDeTiposDeCategoriaPorClima(Categoria.INFERIOR, clima);
		List<PrototipoSuperposicion> superposicionesTiposDeCalzados = obtenerSuperposicionesDeTiposDeCategoriaPorClima(Categoria.CALZADO, clima);
		List<PrototipoSuperposicion> superposicionesTiposDeAccesorios = obtenerSuperposicionesDeTiposDeCategoriaPorClima(Categoria.ACCESORIO, clima);

		List<List<Prenda>> prendasSuperiores = obtenerPrendasQueSatisfacenPrototipo(Categoria.SUPERIOR, superposicionesTiposDeSuperiores);
		List<List<Prenda>> prendasInferiores = obtenerPrendasQueSatisfacenPrototipo(Categoria.INFERIOR, superposicionesTiposDeInferiores);
		List<List<Prenda>> calzados = obtenerPrendasQueSatisfacenPrototipo(Categoria.CALZADO, superposicionesTiposDeCalzados);
		List<List<Prenda>> accesorios = obtenerPrendasQueSatisfacenPrototipo(Categoria.ACCESORIO, superposicionesTiposDeAccesorios);

		List<Sugerencia> ret = Lists
				.cartesianProduct(prendasSuperiores, prendasInferiores, calzados, accesorios)
				.stream()
				.map(result -> new Sugerencia(new Atuendo(result.get(0), result.get(1).get(0), result.get(2).get(0), result.get(3))))
				.filter(sinPrendasEnUsoPorOtroUsuario)
				.collect(Collectors.toList());

		if (ret.isEmpty()) throw new SinSugerenciasPosiblesException();

		return ret;
	}

	private List<PrototipoSuperposicion> obtenerSuperposicionesDeTiposDeCategoriaPorClima(Categoria categoria, Clima clima) {
		double celsius = clima.getTemperatura().toCelsius().getValor();

		return Tipo.obtenerPrototiposSuperposiciones(categoria).stream()
				.filter(
						superposicion -> superposicion.getTemperaturaMinima().getValor() <= celsius && superposicion.getTemperaturaMaxima().getValor() >= celsius
				).collect(Collectors.toList());
	}

	private List<List<Prenda>> obtenerPrendasQueSatisfacenPrototipo(Categoria categoria, List<PrototipoSuperposicion> prototipos) {
		List<Prenda> prendasDeCategoria = this.getPrendasDeCategoria(categoria);

		List<List<Tipo>> todosLosTipos = prototipos.stream().map(PrototipoSuperposicion::getTipos).collect(Collectors.toList());

		return todosLosTipos.stream().flatMap(tipos -> {
			List<List<Prenda>> prendas = tipos.stream().map(tipo -> prendasDeCategoria.stream().filter(prenda -> prenda.getTipo() == tipo).collect(Collectors.toList())).collect(Collectors.toList());
			List<List<Prenda>> prendasCartesiano = Lists.cartesianProduct(prendas);
			return prendasCartesiano.stream();
		}).collect(Collectors.toList());
	}
}
