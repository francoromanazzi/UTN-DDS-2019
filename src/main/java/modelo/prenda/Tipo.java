package modelo.prenda;

import java.util.Collection;
import java.util.EnumSet;
import java.util.stream.Collectors;

public enum Tipo {
	MUSCULOSA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER), NivelDeAbrigo.POCO),
	REMERA_MANGA_CORTA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER), NivelDeAbrigo.POCO),
	REMERA_MANGA_LARGA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER), NivelDeAbrigo.NORMAL) {
		@Override
		public Collection<Tipo> getRequisitosParaUsarse() {
			return EnumSet.of(Tipo.REMERA_MANGA_CORTA, this);
		}
	},
	CAMISA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER), NivelDeAbrigo.NORMAL),
	BUZO(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER, Material.LANA), NivelDeAbrigo.NORMAL) {
		@Override
		public Collection<Tipo> getRequisitosParaUsarse() {
			return EnumSet.of(Tipo.MUSCULOSA, Tipo.REMERA_MANGA_CORTA, Tipo.REMERA_MANGA_LARGA, Tipo.CAMISA);
		}
	},
	CAMPERA(Categoria.SUPERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER, Material.CUERO), NivelDeAbrigo.MUCHO) {
		@Override
		public Collection<Tipo> getRequisitosParaUsarse() {
			return EnumSet.of(Tipo.BUZO, Tipo.REMERA_MANGA_CORTA, Tipo.REMERA_MANGA_LARGA, Tipo.CAMISA);
		}
	},

	PANTALON_CORTO(Categoria.INFERIOR, EnumSet.of(Material.ALGODON, Material.DENIM), NivelDeAbrigo.POCO),
	PANTALON_LARGO(Categoria.INFERIOR, EnumSet.of(Material.ALGODON, Material.DENIM), NivelDeAbrigo.MUCHO),
	BERMUDA(Categoria.INFERIOR, EnumSet.of(Material.ALGODON), NivelDeAbrigo.POCO),
	POLLERA(Categoria.INFERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER), NivelDeAbrigo.POCO),
	CALZA(Categoria.INFERIOR, EnumSet.of(Material.ALGODON, Material.POLIESTER), NivelDeAbrigo.NORMAL),

	ZAPATOS(Categoria.CALZADO, EnumSet.of(Material.CUERO), NivelDeAbrigo.COMODIN),
	ZAPATILLAS(Categoria.CALZADO, EnumSet.of(Material.CUERO), NivelDeAbrigo.COMODIN),
	BOTAS(Categoria.CALZADO, EnumSet.of(Material.CUERO), NivelDeAbrigo.MUCHO),
	OJOTAS(Categoria.CALZADO, EnumSet.of(Material.GOMA, Material.POLIESTER), NivelDeAbrigo.POCO),

	// Los requisitos de los accesorios tienen una dependencia con una estructura piramidal para evitar loops infinitos
	// Cuando mayor nivel de abrigo tengan, serán más abarcativos (gorro, bufanda)
	// Los menos abarcativos son los comodines ya que en principio podrían usarse siempre
	// excepto que no puedan superponers con otra de un nivel de abrigo mayor (ej la gorra "pierde" contra el gorro)
	GORRA(Categoria.ACCESORIO, EnumSet.of(Material.POLIESTER), NivelDeAbrigo.COMODIN) {
		@Override
		public Collection<Tipo> getRequisitosParaUsarse() {
			return EnumSet.of(this);
		}
	},
	ANTEOJOS(Categoria.ACCESORIO, EnumSet.of(Material.PLASTICO), NivelDeAbrigo.COMODIN) {
		@Override
		public Collection<Tipo> getRequisitosParaUsarse() {
			return EnumSet.of(this, Tipo.GORRA);
		}
	},
	RELOJ(Categoria.ACCESORIO, EnumSet.of(Material.PLATA, Material.ORO), NivelDeAbrigo.COMODIN) {
		@Override
		public Collection<Tipo> getRequisitosParaUsarse() {
			return EnumSet.of(this, Tipo.GORRA, Tipo.ANTEOJOS);
		}
	},
	COLGANTE(Categoria.ACCESORIO, EnumSet.of(Material.PLATA, Material.ORO), NivelDeAbrigo.COMODIN) {
		@Override
		public Collection<Tipo> getRequisitosParaUsarse() {
			return EnumSet.of(this, Tipo.GORRA, Tipo.ANTEOJOS, Tipo.RELOJ);
		}
	},
	GORRO(Categoria.ACCESORIO, EnumSet.of(Material.LANA, Material.POLIESTER), NivelDeAbrigo.MUCHO) {
		@Override
		public Collection<Tipo> getRequisitosParaUsarse() {
			return EnumSet.of(this, Tipo.ANTEOJOS, Tipo.RELOJ, Tipo.COLGANTE); // Le saqué Tipo.GORRA
		}
	},
	BUFANDA(Categoria.ACCESORIO, EnumSet.of(Material.LANA), NivelDeAbrigo.MUCHO) {
		@Override
		public Collection<Tipo> getRequisitosParaUsarse() {
			return EnumSet.allOf(Tipo.class).stream().filter(tipo -> tipo.getCategoria() == Categoria.ACCESORIO).collect(Collectors.toList());
		}
	};

	private final Categoria categoria;
	private final Collection<Material> materialesPosibles;
	private final NivelDeAbrigo nivelDeAbrigo;

	Tipo(Categoria categoria, Collection<Material> materiales, NivelDeAbrigo nivelDeAbrigo) {
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

	// Para las prendas que necesiten alguna otra para poder usarse, lo overridean:
	public Collection<Tipo> getRequisitosParaUsarse() {
		return EnumSet.of(this); // Como le digo que necesita de sí misma (this), en realidad le estoy diciendo que puede usarse sola
	}

	public boolean puedeSerDeMaterial(Material mat) {
		return materialesPosibles.contains(mat);
	}
}