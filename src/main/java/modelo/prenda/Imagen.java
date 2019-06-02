package modelo.prenda;

import excepciones.ExtensionDeImagenErroneaException;
import excepciones.ImagenNoPudoSerLeidaException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class Imagen {
	public static final int HEIGHT = 500, WIDTH = 500;
	private final BufferedImage imagen;

	public Imagen(File arch) throws ImagenNoPudoSerLeidaException, ExtensionDeImagenErroneaException {
		this.imagen = leerYNormalizarImagen(arch);
	}

	public BufferedImage getImagen() {
		return this.imagen;
	}

	public void escribirEnArchivo(String url) throws IOException {
		String separador = Pattern.quote(".");
		String[] parts = url.split(separador);
		ImageIO.write(this.imagen, parts[parts.length - 1], new File(url));
	}

	private BufferedImage leerYNormalizarImagen(File archivoImagen) throws ImagenNoPudoSerLeidaException, ExtensionDeImagenErroneaException {
		if (!archivoImagen.getPath().endsWith(".jpg") && !archivoImagen.getPath().endsWith(".jpeg") && !archivoImagen.getPath().endsWith(".png"))
			throw new ExtensionDeImagenErroneaException();

		BufferedImage imagenOriginal;
		try {
			imagenOriginal = ImageIO.read(archivoImagen);

		} catch (IOException e) {
			throw new ImagenNoPudoSerLeidaException();
		}
		return resize(imagenOriginal);
	}

	private BufferedImage resize(BufferedImage img) {
		Image tmp = img.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}
}
