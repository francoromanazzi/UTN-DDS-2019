package UTN.QueMePongo;

import org.junit.Test;
import modelo.prenda.Imagen;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class TestImagen {
	private final String
			UrlArchivoGrande = "src/test/resources/img/RemeraArchivoGrande.jpg",
			UrlArchivoChico = "src/test/resources/img/RemeraArchivoChico.jpg",
			UrlArchivoGrandeReescalado = "src/test/resources/img/RemeraArchivoGrandeReescalado.jpg",
			UrlArchivoChicoReescalado = "src/test/resources/img/RemeraArchivoChicoReescalado.jpg";

	@Test
	public void deberiaPoderReescalarUnaImagenGrande() throws IOException {
		Imagen imagenReescalada = new Imagen(new File(UrlArchivoGrande));
		imagenReescalada.escribirEnArchivo(UrlArchivoGrandeReescalado);

		assertEquals(imagenReescalada.getImagen().getWidth(), Imagen.WIDTH);
		assertEquals(imagenReescalada.getImagen().getHeight(), Imagen.HEIGHT);
	}

	@Test
	public void deberiaPoderReescalarUnaImagenChica() throws IOException {
		Imagen imagenReescalada = new Imagen(new File(UrlArchivoChico));
		imagenReescalada.escribirEnArchivo(UrlArchivoChicoReescalado);

		assertEquals(imagenReescalada.getImagen().getWidth(), Imagen.WIDTH);
		assertEquals(imagenReescalada.getImagen().getHeight(), Imagen.HEIGHT);
	}
}
