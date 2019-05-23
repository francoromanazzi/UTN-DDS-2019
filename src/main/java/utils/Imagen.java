package utils;

import excepciones.ImagenNoPudoSerCargadaException;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;

public final class Imagen {
	public static ImageIcon leerYNormalizarImagen(File archivoImagen) throws ImagenNoPudoSerCargadaException {
		try {
			if(!archivoImagen.getPath().endsWith(".jpg") && !archivoImagen.getPath().endsWith(".png"))
				throw new ImagenNoPudoSerCargadaException();
			byte[] bytesImagen = new byte[1024*100];
			FileInputStream entry = new FileInputStream(archivoImagen);
			entry.read(bytesImagen);
			entry.close();
			return new ImageIcon(bytesImagen);
		} catch (Exception e) {
			throw new ImagenNoPudoSerCargadaException();
		}
	}
}
