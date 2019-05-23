package UTN.QueMePongo;

import excepciones.ImagenNoPudoSerCargadaException;
import excepciones.parametros_nulos.ColorSecundarioNoPuedeSerNuloException;
import excepciones.ColoresIgualesException;
import excepciones.MaterialNoTieneSentidoParaEseTipoException;
import excepciones.parametros_nulos.ImagenNoPuedeSerNulaException;
import excepciones.parametros_nulos.TipoNoPuedeSerNuloException;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import utils.Imagen;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class TestPrenda {

	@Test(expected = TipoNoPuedeSerNuloException.class)
	public void exceptionSiPrendaTieneTipoNulo() {
		new Prenda(null, Material.ALGODON, new Color(0, 0,  0), Optional.empty(), Optional.empty());
	}

	@Test(expected = ColorSecundarioNoPuedeSerNuloException.class)
	public void exceptionSiColorSecundarioTieneTipoNulo() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0,  0), null, Optional.empty());
	}

	@Test(expected = ImagenNoPuedeSerNulaException.class)
	public void exceptionSiImagenTieneTipoNulo() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), null);
	}

	@Test(expected = ColoresIgualesException.class)
	public void exceptionSiColoresPrincipalYSecundarioSonIguales() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(128, 128, 128), Optional.of(new Color(128, 128, 128)), Optional.empty());
	}

	@Test(expected = MaterialNoTieneSentidoParaEseTipoException.class)
	public void exceptionSiTipoYMaterialSonIncompatibles() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ORO, new Color(0, 0, 0), Optional.empty(), Optional.empty());
	}

	@Test
	public void deberiaPoderCargarImagenesChicas() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.of(new File("src/test/resources/img/RemeraArchivoChico.jpg")));
	}

	@Test
	public void deberiaPoderCargarImagenesGrandas() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.of(new File("src/test/resources/img/RemeraArchivoGrande.jpg")));
	}

	@Test(expected = ImagenNoPudoSerCargadaException.class)
	public void deberiaFallarSiElPathNoTieneNada() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.of(new File("src/test/resources/img/vacio.png")));
	}

	@Test(expected = ImagenNoPudoSerCargadaException.class)
	public void deberiaFallarSiElPathNoEsDeUnaImagen() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.of(new File("src/test/resources/img/texto.txt")));
	}
	
	@Test
	public void reescaladoImagenGrande() throws IOException {
		String pathIMagenGrande = "src/test/resources/img/RemeraArchivoGrande.jpg";
		File arch = new File(pathIMagenGrande);
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.of(arch));
		BufferedImage im = Imagen.leerYNormalizarImagen(arch);
        
		File outputfile = new File("src/test/resources/img/RemeraArchivoGrandeCopia.png");
		ImageIO.write(im, "png", outputfile);
		//Crea una copia de la imagen pero reescalada - ver src/test/resources/img/
	}
}
