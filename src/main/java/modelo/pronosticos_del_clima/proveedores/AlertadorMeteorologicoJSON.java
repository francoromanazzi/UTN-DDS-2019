package modelo.pronosticos_del_clima.proveedores;

import modelo.alerta_meteorologica.AlertaMeteorologica;

import java.util.Optional;

public interface AlertadorMeteorologicoJSON {
	Optional<AlertaMeteorologica> toAlertaMeteorologica();
}
