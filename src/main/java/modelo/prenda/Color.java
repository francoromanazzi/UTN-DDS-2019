package modelo.prenda;

import excepciones.LimiteExcedidoEnColorException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
@Table(name = "colores")
public class Color {
	@Id
	@GeneratedValue
	private long Id;
	private int rojo, verde, azul;

	public Color() {
	}

	public Color(int rojo, int verde, int azul) {
		if (Stream.of(rojo, verde, azul).map(color -> Objects.requireNonNull(color, "El color es obligatorio"))
				.anyMatch(color -> color < 0 || color > 255))
			throw new LimiteExcedidoEnColorException();

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

	public Long getId() {
		return this.Id;
	}

	@Override
	public String toString() {
		return "(" + rojo + ", " + verde + ", " + azul + ")";
	}

	/* Hay que darle un string de tipo (111,222,333) */
	public static Color fromString(String s){
		String[] parsed = s.split("\\D+");

		// parsed[0] es un string nulo
		String redString = parsed[1];
		String greenString = parsed[2];
		String blueString = parsed[3];

		int red = Integer.parseInt(redString);
		int green = Integer.parseInt(greenString);
		int blue = Integer.parseInt(blueString);

		return new Color(red, green, blue);
	}
}
