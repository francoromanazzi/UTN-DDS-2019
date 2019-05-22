package modelo.prenda;

import excepciones.ColoresIgualesException;
import excepciones.MaterialNoTieneSentidoParaEseTipoException;
import excepciones.parametros_nulos.ColorPrincipalNoPuedeSerNuloException;
import excepciones.parametros_nulos.ColorSecundarioNoPuedeSerNuloException;
import excepciones.parametros_nulos.MaterialNoPuedeSerNuloException;
import excepciones.parametros_nulos.TipoNoPuedeSerNuloException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javax.swing.ImageIcon;

public class Prenda {
	private final Tipo tipo;
	private final Material material;
	private final Color colorPrincipal;
	private final Optional<Color> colorSecundario;
	private ImageIcon imagen = new ImageIcon();

	public Prenda(Tipo tipo, Material material, Color colorPrincipal, Optional<Color> colorSecundario)
			throws TipoNoPuedeSerNuloException, MaterialNoPuedeSerNuloException, ColorPrincipalNoPuedeSerNuloException, ColorSecundarioNoPuedeSerNuloException,
			MaterialNoTieneSentidoParaEseTipoException, ColoresIgualesException {
		if (tipo != null) this.tipo = tipo;
		else throw new TipoNoPuedeSerNuloException();

		if (material != null) this.material = material;
		else throw new MaterialNoPuedeSerNuloException();

		if (colorPrincipal != null) this.colorPrincipal = colorPrincipal;
		else throw new ColorPrincipalNoPuedeSerNuloException();

		if (colorSecundario != null) this.colorSecundario = colorSecundario;
		else throw new ColorSecundarioNoPuedeSerNuloException();

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
	
	public void setImagen(File archivoImagen) throws IOException {
		try {
			byte[] bytesImagen = new byte[1024*100];
			FileInputStream entry = new FileInputStream(archivoImagen);
			entry.read(bytesImagen);
			entry.close();
			this.imagen = new ImageIcon(bytesImagen);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ImageIcon getImagen() {
		return this.imagen;
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

	@Override
	public String toString() {
		return tipo.toString() + " de " + material.toString();
	}
}
