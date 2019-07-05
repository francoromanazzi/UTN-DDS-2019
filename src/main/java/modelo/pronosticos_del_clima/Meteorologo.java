package modelo.pronosticos_del_clima;

import excepciones.ProveedorDeClimaSeCayoException;
import modelo.alerta_meteorologica.AlertaMeteorologica;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class Meteorologo {
	public abstract List<Pronostico> obtenerPronosticos(Optional<Consumer<List<Pronostico>>> callback) throws ProveedorDeClimaSeCayoException;

	public abstract List<AlertaMeteorologica> obtenerAlertasMeteorologicas();
}
