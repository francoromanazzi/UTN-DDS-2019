package presentacion.view_model;

import excepciones.FechaFinDebeSerPosteriorAFechaInicioException;
import excepciones.parametros_nulos.FechaFinNoPuedeSerNulaException;
import excepciones.parametros_nulos.FechaInicioNoPuedeSerNulaException;
import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import modelo.evento.TipoEvento;
import org.uqbar.commons.model.annotations.Observable;

import java.time.LocalDateTime;

@Observable
public class EventoObservable extends Evento {

	public EventoObservable(String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFin, FrecuenciaEvento frecuencia, TipoEvento tipo) throws FechaFinDebeSerPosteriorAFechaInicioException, FechaInicioNoPuedeSerNulaException, FechaFinNoPuedeSerNulaException {
		super(titulo, fechaInicio, fechaFin, frecuencia, tipo);
	}

	public boolean getSugerenciasFueronGeneradas() {
		return false;
	}
}
