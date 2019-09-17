package utils;

import modelo.prenda.Imagen;

import javax.imageio.ImageIO;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Converter
public class BufferedImageToByteArrConverter implements AttributeConverter<BufferedImage, byte[]> {

	@Override
	public byte[] convertToDatabaseColumn(BufferedImage bufferedImage) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "jpg", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			return new byte[0];
		}
	}

	@Override
	public BufferedImage convertToEntityAttribute(byte[] bytes) {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		try {
			return ImageIO.read(bais);
		} catch (IOException e) { // Retorno una imagen en blanco
			BufferedImage bufferedImage = new BufferedImage(Imagen.WIDTH, Imagen.HEIGHT, BufferedImage.TYPE_INT_RGB);

			Graphics2D g2d = bufferedImage.createGraphics();
			g2d.setColor(Color.white);
			g2d.fillRect(0, 0, Imagen.WIDTH, Imagen.HEIGHT);

			g2d.dispose();

			return bufferedImage;
		}
	}
}
