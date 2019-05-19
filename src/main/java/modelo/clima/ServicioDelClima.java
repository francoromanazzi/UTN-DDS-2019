package modelo.clima;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioDelClima {
	private static ServicioDelClima ourInstance = new ServicioDelClima();
	private List<Clima> pronosticos = new ArrayList<>();
	private List<Meteorologo> meteorologos = new ArrayList<>();

	public static ServicioDelClima getInstance() {
		return ourInstance;
	}

	private ServicioDelClima() {
	}

	public List<Meteorologo> getMeteorologos() {
		return meteorologos;
	}

	public void setMeteorologos(List<Meteorologo> meteorologos) {
		this.meteorologos = meteorologos;
	}

	public List<Clima> getPronosticos() {
		return pronosticos;
	}

	public void setPronosticos(List<Clima> pronosticos) {
		this.pronosticos = pronosticos;
	}

	public void agregarMeteorologo(Meteorologo meteorologo) {
		meteorologos.add(meteorologo);
	}

	public Clima obtenerClima() {
		// Reviso si ya lo tenía cacheado
		if(obtenerPronosticosRazonables().isEmpty()) {
			// Consulto a un meteorologo
			pronosticos = meteorologos.get(0).obtenerPronosticos(); // TODO: Cambiar el get(0)
		}

		return obtenerPronosticosRazonables().get(0); // TODO: Seleccionar el más cercano a ahora
	}

	private List<Clima> obtenerPronosticosRazonables() {
		LocalDateTime fechaActual = LocalDateTime.now();
		return pronosticos.stream().filter(clima -> clima.getFecha().isBefore(fechaActual.plusMinutes(60))).collect(Collectors.toList());
	}
}
