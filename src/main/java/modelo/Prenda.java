package modelo;

import java.util.Objects;
import java.util.Optional;
import excepciones.ColoresIgualesException;
import excepciones.MaterialNoTieneSentidoParaEseTipoException;

public class Prenda {
	private final Tipo tipo;
	private final Material material;
	private final Color colorPrincipal;
	private final Optional<Color> colorSecundario;

	public Prenda(Tipo tipo, Material material, Color colorPrincipal, Optional<Color> colorSecundario) {
		this.tipo = Objects.requireNonNull(tipo, "Tipo no puede ser nulo");
		this.material = Objects.requireNonNull(material, "Material no puede ser nulo");
		this.colorPrincipal = Objects.requireNonNull(colorPrincipal, "Color principal no puede ser nulo");
		this.colorSecundario = Objects.requireNonNull(colorSecundario,
				"Color secundario es opcional, pero no puede ser nulo");

		validarColoresDistintos();
		validarMaterialTieneSentido();
	}

	private void validarMaterialTieneSentido() throws MaterialNoTieneSentidoParaEseTipoException {
		if (!tipo.puedeSerDeMaterial(material))
			throw new MaterialNoTieneSentidoParaEseTipoException();
	}

	private void validarColoresDistintos() throws ColoresIgualesException {
		if (colorSecundario.filter(color -> color.esIgualA(colorPrincipal)).isPresent())
			throw new ColoresIgualesException();
	}

	public Tipo getTipo() {
		return tipo;
	}

	public Material getMaterial() {
		return material;
	}

	public Color getColorPrincipal() {
		return colorPrincipal;
	}

	public Categoria getCategoria() {
		return this.tipo.getCategoria();
	}
}
