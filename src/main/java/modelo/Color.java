package modelo;

import java.util.Objects;
import java.util.stream.Stream;

public class Color {
	private int rojo, verde, azul;

	public Color(int rojo, int verde, int azul) {
		// Validar colores entre 0 y 255 y que no sean nulos
		if (Stream.of(rojo, verde, azul).map(color -> Objects.requireNonNull(color, "El color es obligatorio"))
				.anyMatch(color -> color < 0 || color > 255))
			throw new IllegalArgumentException("Los colores RGB deben estar definidos entre 0 y 255");

		this.rojo = rojo;
		this.verde = verde;
		this.azul = azul;
	}

	public int getRojo() {
		return rojo;
	}

	public int getVerde() {
		return verde;
	}

	public int getAzul() {
		return azul;
	}

	public boolean esIgualA(Color color) {
		return this.rojo == color.getRojo() && this.azul == color.getAzul() && this.verde == color.getVerde();
	}
}
