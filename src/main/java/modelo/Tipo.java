package modelo;

import java.util.Collection;
import java.util.EnumSet;

public enum Tipo {
	REMERA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	CAMISA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	MUSCULOSA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	CAMPERA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER, Material.CUERO)),
	BUZO(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER, Material.LANA)),

	PANTALON(Categoria.INFERIOR, EnumSet.of(Material.ALGODON, Material.DENIM)),
	BERMUDA(Categoria.INFERIOR, EnumSet.of(Material.ALGODON)),
	POLLERA(Categoria.INFERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER)),
	CALZA(Categoria.INFERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER)),

	ZAPATOS(Categoria.CALZADO, EnumSet.of(Material.CUERO)),
	ZAPATILLAS(Categoria.CALZADO, EnumSet.of(Material.CUERO)),
	BOTAS(Categoria.CALZADO, EnumSet.of(Material.CUERO)),
	OJOTAS(Categoria.CALZADO, EnumSet.of(Material.GOMA, Material.POLIESTER)),

	GORRA(Categoria.ACCESORIO, EnumSet.of(Material.POLIESTER)),
	BUFANDA(Categoria.ACCESORIO, EnumSet.of(Material.LANA)),
	ANTEOJOS(Categoria.ACCESORIO, EnumSet.of(Material.PLASTICO)),
	RELOJ(Categoria.ACCESORIO, EnumSet.of(Material.PLATA, Material.ORO)),
	COLGANTE(Categoria.ACCESORIO, EnumSet.of(Material.PLATA, Material.ORO));

	private final Categoria categoria;
	private final Collection<Material> materialesPosibles;

	private Tipo(Categoria categoria, Collection<Material> materiales) {
		this.categoria = categoria;
		this.materialesPosibles = materiales;
	}

	//TODO sacar todos los getters (o metodos) que no se usen
	public Categoria getCategoria() {
		return categoria;
	}

	public boolean puedeSerDeMaterial(Material mat) {
		return materialesPosibles.contains(mat);
	}
}