package utils;

import excepciones.ImagenNoPudoSerCargadaException;
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Imagen {
	public static BufferedImage leerYNormalizarImagen(File archivoImagen) throws ImagenNoPudoSerCargadaException {
		if(!archivoImagen.getPath().endsWith(".jpg") && !archivoImagen.getPath().endsWith(".png"))
			throw new ImagenNoPudoSerCargadaException();
		BufferedImage imagen = null;
		BufferedImage resized;
		try {
			imagen = ImageIO.read(archivoImagen);
		    resized = resize(imagen, 500, 500); //500px x 500px
			
		} catch (IOException e) {
			throw new ImagenNoPudoSerCargadaException();
		}
		return resized;
	}
	
	private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
