package UTN.QueMePongo;

import modelo.Color;
import org.junit.Test;

import excepciones.LimiteExcedidoEnColorException;

import static org.junit.Assert.assertTrue;

public class TestColor {

	@Test(expected = LimiteExcedidoEnColorException.class)
	public void ExceptionSiRgbNoEstaEntre0y255() {
		new Color(0, 0, 300);
	}

	@Test
	public void verificarSiDosColoresSonIguales() {
		assertTrue(new Color(0, 0, 0).esIgualA(new Color(0, 0, 0)));
	}
}
