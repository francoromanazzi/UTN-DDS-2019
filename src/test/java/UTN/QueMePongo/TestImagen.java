package UTN.QueMePongo;

import org.junit.Test;
import utils.Imagen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestImagen {
	@Test
	public void deberiaPoderReescalarUnaImagenGrande() throws IOException {
		File arch = new File("src/test/resources/img/RemeraArchivoGrande.jpg");
		BufferedImage im = Imagen.leerYNormalizarImagen(arch);

		File outputfile = new File("src/test/resources/img/RemeraArchivoGrandeCopia.png");
		ImageIO.write(im, "png", outputfile);
		//Crea una copia de la imagen pero reescalada - ver src/test/resources/img/
	}

	@Test
	public void deberiaPoderReescalarUnaImagenChica() throws IOException {
		File arch = new File("src/test/resources/img/RemeraArchivoChico.jpg");
		BufferedImage im = Imagen.leerYNormalizarImagen(arch);

		File outputfile = new File("src/test/resources/img/RemeraArchivoChicoCopia.png");
		ImageIO.write(im, "png", outputfile);
		//Crea una copia de la imagen pero reescalada - ver src/test/resources/img/
	}
}
