package modelo.prenda;

import excepciones.ColoresIgualesException;
import excepciones.ExtensionDeImagenErroneaException;
import excepciones.ImagenNoPudoSerLeidaException;
import excepciones.MaterialNoTieneSentidoParaEseTipoException;
import excepciones.parametros_nulos.*;

import javax.persistence.*;
import java.io.File;
import java.util.Optional;

@Entity
@Table(name = "prendas")
public class Prenda {
	@Id
	@GeneratedValue
	private long Id;
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	@Enumerated(EnumType.STRING)
	private Material material;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Color colorPrincipal;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Color colorSecundario;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Imagen imagen;

	public Prenda() {
	}

	public Prenda(Tipo tipo, Material material, Color colorPrincipal, Optional<Color> colorSecundario, Optional<Imagen> imagen)
			throws TipoNoPuedeSerNuloException, MaterialNoPuedeSerNuloException, ColorPrincipalNoPuedeSerNuloException, ColorSecundarioNoPuedeSerNuloException,
			MaterialNoTieneSentidoParaEseTipoException, ColoresIgualesException, ImagenNoPuedeSerNulaException {

		if (tipo != null) this.tipo = tipo;
		else throw new TipoNoPuedeSerNuloException();

		if (material != null) this.material = material;
		else throw new MaterialNoPuedeSerNuloException();

		if (colorPrincipal != null) this.colorPrincipal = colorPrincipal;
		else throw new ColorPrincipalNoPuedeSerNuloException();

		if (colorSecundario != null) this.colorSecundario = colorSecundario.orElse(null);
		else throw new ColorSecundarioNoPuedeSerNuloException();

		if (imagen != null) this.imagen = imagen.orElse(null);
		else throw new ImagenNoPuedeSerNulaException();

		validarColoresDistintos();
		validarMaterialTieneSentido();
	}

	private void validarMaterialTieneSentido() throws MaterialNoTieneSentidoParaEseTipoException {
		if (!tipo.puedeSerDeMaterial(material))
			throw new MaterialNoTieneSentidoParaEseTipoException();
	}

	private void validarColoresDistintos() throws ColoresIgualesException {
		if (colorSecundario != null && colorPrincipal.esIgualA(colorSecundario))
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

	public Optional<Color> getColorSecundario() {
		return Optional.ofNullable(colorSecundario);
	}

	public Optional<Imagen> getImagen() {
		return Optional.ofNullable(imagen);
	}

	public Long getId() {
		return this.Id;
	}

	@Override
	public String toString() {
		return tipo.toString() + " de " + material.toString();
	}
}
