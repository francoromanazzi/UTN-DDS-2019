package modelo.clima;

import excepciones.ClimaNoDisponibleException;
import excepciones.ProveedorDeClimaSeCayoException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioDelClima {
	private static final ServicioDelClima ourInstance = new ServicioDelClima();
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

	public Clima obtenerClima(LocalDateTime fecha) throws ClimaNoDisponibleException {
		// Reviso si ya lo tenía cacheado
		// Si no, le pregunto a mis meteorologos hasta que alguno me lo pueda dar
		for (int i = 0; obtenerPronosticosRazonables(fecha).isEmpty() && i < meteorologos.size(); i++) {
			try {
				pronosticos = meteorologos.get(i).obtenerPronosticos();
			} catch (ProveedorDeClimaSeCayoException ex) {
				System.out.println("El proveedor " + meteorologos.get(i).getClass() + " se cayo");
			}
		}

		if (obtenerPronosticosRazonables(fecha).isEmpty()) {
			throw new ClimaNoDisponibleException();
		}

		return obtenerPronosticosRazonables(fecha)
				.stream()
				.reduce(obtenerPronosticosRazonables(fecha).get(0), (clima1, clima2) ->
						Math.abs(ChronoUnit.MINUTES.between(fecha, clima1.getFecha())) <= Math.abs(ChronoUnit.MINUTES.between(fecha, clima2.getFecha()))
								? clima1 : clima2);
	}

	private List<Clima> obtenerPronosticosRazonables(LocalDateTime fecha) {
		return pronosticos.stream().filter(clima -> Math.abs(ChronoUnit.MINUTES.between(fecha, clima.getFecha())) <= 60).collect(Collectors.toList());
	}
}
