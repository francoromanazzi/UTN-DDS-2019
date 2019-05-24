package UTN.QueMePongo;

import org.junit.Test;
import utils.Imagen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestImagen {
	private final File
			archivoGrande = new File("src/test/resources/img/RemeraArchivoGrande.jpg"),
			archivoGrandeReescalado = new File("src/test/resources/img/RemeraArchivoGrandeReescalado.jpg"),
			archivoChico = new File("src/test/resources/img/RemeraArchivoChico.jpg"),
			archivoChicoReescalado = new File("src/test/resources/img/RemeraArchivoChicoReescalado.jpg");

	@Test
	public void deberiaPoderReescalarUnaImagenGrande() throws IOException {
		BufferedImage imagenOriginal = Imagen.leerYNormalizarImagen(archivoGrande);

		ImageIO.write(imagenOriginal, "png", archivoGrandeReescalado); //Crea una copia de la imagen pero reescalada - ver src/test/resources/img/

		BufferedImage imagenReescalada = ImageIO.read(archivoGrandeReescalado);

		assertEquals(imagenReescalada.getWidth(), Imagen.WIDTH);
		assertEquals(imagenReescalada.getHeight(), Imagen.HEIGHT);
	}

	@Test
	public void deberiaPoderReescalarUnaImagenChica() throws IOException {
		BufferedImage imagenOriginal = Imagen.leerYNormalizarImagen(archivoChico);

		ImageIO.write(imagenOriginal, "png", archivoChicoReescalado); //Crea una copia de la imagen pero reescalada - ver src/test/resources/img/

		BufferedImage imagenReescalada = ImageIO.read(archivoChicoReescalado);

		assertEquals(imagenReescalada.getWidth(), Imagen.WIDTH);
		assertEquals(imagenReescalada.getHeight(), Imagen.HEIGHT);
	}
}
