package modelo.prenda;

import excepciones.ExtensionDeImagenErroneaException;
import excepciones.ImagenNoPudoSerLeidaException;
import utils.BufferedImageToByteArrConverter;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

@Entity
@Table(name = "imagenes")
public class Imagen {
	@Id
	@GeneratedValue
	private long Id;

	public static final int HEIGHT = 500, WIDTH = 500;

	@Convert(converter = BufferedImageToByteArrConverter.class)
	private BufferedImage imagen;

	public Imagen(File arch) throws ImagenNoPudoSerLeidaException, ExtensionDeImagenErroneaException {
		this.imagen = leerYNormalizarImagen(arch);
	}

	public Imagen(BufferedImage imagen) {
		this.imagen = resize(imagen);
	}

	public Imagen() {
	}

	public long getId() {
		return Id;
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

	private BufferedImage resize(BufferedImage originalImage) {
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		Image tmp = originalImage.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
		BufferedImage resizedImage = new BufferedImage(WIDTH, HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(tmp, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();

		return resizedImage;
	}
}
