package modelo.prenda;

import java.util.Collection;
import java.util.EnumSet;

public enum Tipo {
	REMERA_MANGA_CORTA(Categoria.SUPERIOR, NivelDeAbrigo.POCO, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	REMERA_MANGA_LARGA(Categoria.SUPERIOR, NivelDeAbrigo.NORMAL, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	CAMISA(Categoria.SUPERIOR, NivelDeAbrigo.NORMAL, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	MUSCULOSA(Categoria.SUPERIOR, NivelDeAbrigo.NADA, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	CAMPERA(Categoria.SUPERIOR, NivelDeAbrigo.MUCHO, EnumSet.of(Material.ALGODON, Material.POLIESTER, Material.CUERO)),
	BUZO(Categoria.SUPERIOR, NivelDeAbrigo.MUCHO, EnumSet.of(Material.ALGODON, Material.POLIESTER, Material.LANA)),

	PANTALON_CORTO(Categoria.INFERIOR, NivelDeAbrigo.POCO, EnumSet.of(Material.ALGODON, Material.DENIM)),
	PANTALON_LARGO(Categoria.INFERIOR, NivelDeAbrigo.MUCHO, EnumSet.of(Material.ALGODON, Material.DENIM)),
	BERMUDA(Categoria.INFERIOR, NivelDeAbrigo.POCO, EnumSet.of(Material.ALGODON)),
	POLLERA(Categoria.INFERIOR, NivelDeAbrigo.POCO, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	CALZA(Categoria.INFERIOR, NivelDeAbrigo.NORMAL, EnumSet.of(Material.ALGODON, Material.POLIESTER)),

	ZAPATOS(Categoria.CALZADO, NivelDeAbrigo.NORMAL, EnumSet.of(Material.CUERO)),
	ZAPATILLAS(Categoria.CALZADO, NivelDeAbrigo.NORMAL, EnumSet.of(Material.CUERO)),
	BOTAS(Categoria.CALZADO, NivelDeAbrigo.MUCHO, EnumSet.of(Material.CUERO)),
	OJOTAS(Categoria.CALZADO, NivelDeAbrigo.NADA, EnumSet.of(Material.GOMA, Material.POLIESTER)),

	GORRA(Categoria.ACCESORIO, NivelDeAbrigo.POCO, EnumSet.of(Material.POLIESTER)),
	BUFANDA(Categoria.ACCESORIO, NivelDeAbrigo.MUCHO, EnumSet.of(Material.LANA)),
	ANTEOJOS(Categoria.ACCESORIO, NivelDeAbrigo.NADA, EnumSet.of(Material.PLASTICO)),
	RELOJ(Categoria.ACCESORIO, NivelDeAbrigo.NADA, EnumSet.of(Material.PLATA, Material.ORO)),
	COLGANTE(Categoria.ACCESORIO, NivelDeAbrigo.NADA, EnumSet.of(Material.PLATA, Material.ORO));

	private final Categoria categoria;
	private final NivelDeAbrigo nivelDeAbrigo;
	private final Collection<Material> materialesPosibles;

	Tipo(Categoria categoria, NivelDeAbrigo nivelDeAbrigo, Collection<Material> materiales) {
		this.categoria = categoria;
		this.nivelDeAbrigo = nivelDeAbrigo;
		this.materialesPosibles = materiales;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public NivelDeAbrigo getNivelDeAbrigo() {
		return nivelDeAbrigo;
	}

	public boolean puedeSerDeMaterial(Material mat) {
		return materialesPosibles.contains(mat);
	}
}