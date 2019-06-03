package modelo.prenda;

import modelo.pronosticos_del_clima.clima.temperatura.Temperatura;

import java.util.List;

public class PrototipoSuperposicion {
	private final List<Tipo> tipos;
	private final Temperatura temperaturaMinima, temperaturaMaxima;

	public PrototipoSuperposicion(List<Tipo> tipos, Temperatura temperaturaMinima, Temperatura temperaturaMaxima) {
		this.tipos = tipos;
		this.temperaturaMinima = temperaturaMinima;
		this.temperaturaMaxima = temperaturaMaxima;
	}

	public List<Tipo> getTipos() {
		return tipos;
	}

	public Temperatura getTemperaturaMinima() {
		return temperaturaMinima;
	}

	public Temperatura getTemperaturaMaxima() {
		return temperaturaMaxima;
	}
}
