package modelo;

public class Color {
	private int rojo;
	private int verde;
	private int azul;
	
	public Color(int rojo, int verde, int azul) {
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
