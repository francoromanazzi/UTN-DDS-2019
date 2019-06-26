package modelo.prenda;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import modelo.pronosticos_del_clima.clima.temperatura.Celsius;
import modelo.pronosticos_del_clima.clima.temperatura.TemperaturaMasInfinito;
import modelo.pronosticos_del_clima.clima.temperatura.TemperaturaMenosInfinito;

import java.util.*;
import java.util.stream.Collectors;

public enum Tipo {
	MUSCULOSA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	REMERA_MANGA_CORTA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	REMERA_MANGA_LARGA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	CAMISA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	BUZO(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER, Material.LANA)),
	CAMPERA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER, Material.CUERO)),

	PANTALON_CORTO(Categoria.INFERIOR, EnumSet.of(Material.ALGODON, Material.DENIM)),
	PANTALON_LARGO(Categoria.INFERIOR, EnumSet.of(Material.ALGODON, Material.DENIM)),
	BERMUDA(Categoria.INFERIOR, EnumSet.of(Material.ALGODON)),
	POLLERA(Categoria.INFERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	CALZA(Categoria.INFERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER)),

	ZAPATOS(Categoria.CALZADO, EnumSet.of(Material.CUERO)),
	ZAPATILLAS(Categoria.CALZADO, EnumSet.of(Material.CUERO)),
	BOTAS(Categoria.CALZADO, EnumSet.of(Material.CUERO)),
	OJOTAS(Categoria.CALZADO, EnumSet.of(Material.GOMA, Material.POLIESTER)),

	GORRA(Categoria.ACCESORIO, EnumSet.of(Material.POLIESTER)),
	ANTEOJOS(Categoria.ACCESORIO, EnumSet.of(Material.PLASTICO)),
	RELOJ(Categoria.ACCESORIO, EnumSet.of(Material.PLATA, Material.ORO)),
	COLGANTE(Categoria.ACCESORIO, EnumSet.of(Material.PLATA, Material.ORO)),
	GORRO(Categoria.ACCESORIO, EnumSet.of(Material.LANA, Material.POLIESTER)),
	GUANTES(Categoria.ACCESORIO, EnumSet.of(Material.POLIESTER, Material.LANA)),
	BUFANDA(Categoria.ACCESORIO, EnumSet.of(Material.LANA));

	private final Categoria categoria;
	private final Collection<Material> materialesPosibles;
	private static final Map<Categoria, List<PrototipoSuperposicion>> prototiposSuperposicionesPorCategoria = ImmutableMap.of(
			Categoria.SUPERIOR, Arrays.asList(
					new PrototipoSuperposicion(Arrays.asList(Tipo.MUSCULOSA),
							new Celsius(25), new TemperaturaMasInfinito()),
					new PrototipoSuperposicion(Arrays.asList(Tipo.REMERA_MANGA_CORTA),
							new Celsius(22), new TemperaturaMasInfinito()),
					new PrototipoSuperposicion(Arrays.asList(Tipo.CAMISA),
							new Celsius(17), new Celsius(28)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.REMERA_MANGA_LARGA),
							new Celsius(16), new Celsius(26)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.MUSCULOSA, Tipo.BUZO),
							new Celsius(11), new Celsius(19)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.REMERA_MANGA_CORTA, Tipo.BUZO),
							new Celsius(11), new Celsius(17)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.REMERA_MANGA_LARGA, Tipo.BUZO),
							new Celsius(11), new Celsius(15)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.MUSCULOSA, Tipo.CAMPERA),
							new Celsius(11), new Celsius(17)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.REMERA_MANGA_CORTA, Tipo.CAMPERA),
							new Celsius(10), new Celsius(17)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.REMERA_MANGA_LARGA, Tipo.CAMPERA),
							new Celsius(10), new Celsius(15)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.MUSCULOSA, Tipo.BUZO, Tipo.CAMPERA),
							new Celsius(7), new Celsius(11)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.REMERA_MANGA_CORTA, Tipo.BUZO, Tipo.CAMPERA),
							new Celsius(5), new Celsius(10)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.REMERA_MANGA_LARGA, Tipo.BUZO, Tipo.CAMPERA),
							new Celsius(0), new Celsius(9)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.REMERA_MANGA_CORTA, Tipo.REMERA_MANGA_LARGA, Tipo.BUZO, Tipo.CAMPERA),
							new TemperaturaMenosInfinito(), new Celsius(5))
			),
			Categoria.INFERIOR, Arrays.asList(
					new PrototipoSuperposicion(Arrays.asList(Tipo.PANTALON_CORTO),
							new Celsius(25), new TemperaturaMasInfinito()),
					new PrototipoSuperposicion(Arrays.asList(Tipo.PANTALON_LARGO),
							new TemperaturaMenosInfinito(), new Celsius(28)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.BERMUDA),
							new Celsius(25), new TemperaturaMasInfinito()),
					new PrototipoSuperposicion(Arrays.asList(Tipo.POLLERA),
							new Celsius(22), new TemperaturaMasInfinito()),
					new PrototipoSuperposicion(Arrays.asList(Tipo.CALZA),
							new Celsius(22), new TemperaturaMasInfinito())
			),
			Categoria.CALZADO, Arrays.asList(
					new PrototipoSuperposicion(Arrays.asList(Tipo.ZAPATOS),
							new TemperaturaMenosInfinito(), new TemperaturaMasInfinito()),
					new PrototipoSuperposicion(Arrays.asList(Tipo.ZAPATILLAS),
							new TemperaturaMenosInfinito(), new TemperaturaMasInfinito()),
					new PrototipoSuperposicion(Arrays.asList(Tipo.BOTAS),
							new TemperaturaMenosInfinito(), new Celsius(20)),
					new PrototipoSuperposicion(Arrays.asList(Tipo.OJOTAS),
							new Celsius(25), new TemperaturaMasInfinito())
			),
			Categoria.ACCESORIO, generarPrototiposAccesorios()
	);

	Tipo(Categoria categoria, Collection<Material> materiales) {
		this.categoria = categoria;
		this.materialesPosibles = materiales;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public boolean puedeSerDeMaterial(Material mat) {
		return materialesPosibles.contains(mat);
	}

	private static List<PrototipoSuperposicion> generarPrototiposAccesorios() {
		EnumSet<Tipo> accesorios = EnumSet.allOf(Tipo.class).stream().filter(tipo -> tipo.getCategoria() == Categoria.ACCESORIO).collect(Collectors.toCollection(() -> EnumSet.noneOf(Tipo.class)));

		Set<Set<Tipo>> conjuntoPotencia = Sets.powerSet(accesorios);

		List<PrototipoSuperposicion> combinaciones = conjuntoPotencia.stream().map(set -> new PrototipoSuperposicion(Lists.newArrayList(set), new TemperaturaMenosInfinito(), new TemperaturaMasInfinito())).collect(Collectors.toList());

		// Filtro las prendas validas. Ej: Una gorra y un gorro no pueden ir juntos
		List<PrototipoSuperposicion> combinacionesValidas = combinaciones.stream().filter(
				superposicion -> !superposicion.getTipos().containsAll(Arrays.asList(Tipo.GORRA, Tipo.GORRO))
		).collect(Collectors.toList());

		// Restrinjo rangos de temperatura válidos para las superposiciones con gorro, guantes y/o bufanda
		return combinacionesValidas.stream().map(
				superposicion -> {
					List<Tipo> tipos = superposicion.getTipos();
					if(tipos.containsAll(Arrays.asList(Tipo.GORRO, Tipo.GUANTES, Tipo.BUFANDA))) {
						// 3 accesorios de abrigo
						return new PrototipoSuperposicion(tipos, new TemperaturaMenosInfinito(), new Celsius(7.5));
					}
					else if(tipos.containsAll(Arrays.asList(Tipo.GORRO, Tipo.GUANTES)) || tipos.containsAll(Arrays.asList(Tipo.GORRO, Tipo.BUFANDA)) || tipos.containsAll(Arrays.asList(Tipo.GUANTES, Tipo.BUFANDA))) {
						// 2 accesorios de abrigo
						return new PrototipoSuperposicion(tipos, new TemperaturaMenosInfinito(), new Celsius(9));
					}
					else if(tipos.contains(Tipo.GORRO) || tipos.contains(Tipo.GUANTES) || tipos.contains(Tipo.BUFANDA)) {
						// 1 accesorio de abrigo
						return new PrototipoSuperposicion(tipos, new TemperaturaMenosInfinito(), new Celsius(12));
					}
					else
						// 0 accesorios de abrigo
						return superposicion;
				}
		).collect(Collectors.toList());
	}

	public static List<PrototipoSuperposicion> obtenerPrototiposSuperposiciones(Categoria categoria) {
		return prototiposSuperposicionesPorCategoria.get(categoria);
	}
}