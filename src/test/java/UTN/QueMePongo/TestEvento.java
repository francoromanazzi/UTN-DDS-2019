package UTN.QueMePongo;

import excepciones.FechaFinDebeSerPosteriorAFechaInicioException;
import excepciones.parametros_nulos.FechaFinNoPuedeSerNulaException;
import excepciones.parametros_nulos.FechaInicioNoPuedeSerNulaException;
import modelo.evento.Evento;
import modelo.evento.EventoDiario;
import modelo.evento.FrecuenciaEvento;
import modelo.evento.TipoEvento;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

public class TestEvento {
	private LocalDateTime inicioFiesta;
	private LocalDateTime finFiesta;

	@Before
	public void crearFechas() {
		inicioFiesta = LocalDateTime.of(2019, Month.MAY, 25, 10, 0, 0);
		finFiesta = LocalDateTime.of(2019, Month.MAY, 25, 15, 0, 0);
	}

	@Test(expected = FechaFinDebeSerPosteriorAFechaInicioException.class)
	public void fechaInicialDespuesDeLaFechaFin() {
		new Evento("", finFiesta, inicioFiesta, new FrecuenciaEvento() {
			@Override
			public long getPerido() {
				return 0;
			}
		}, TipoEvento.INFORMAL);
	}

	@Test(expected = FechaInicioNoPuedeSerNulaException.class)
	public void fechaInicialNula() {
		new Evento("", null, finFiesta, new EventoDiario(),TipoEvento.INFORMAL);
	}

	@Test(expected = FechaFinNoPuedeSerNulaException.class)
	public void fechaFinNula() {
		new Evento("", inicioFiesta, null, new EventoDiario(),TipoEvento.INFORMAL);
	}
}
