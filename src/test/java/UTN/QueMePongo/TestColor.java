package UTN.QueMePongo;

import excepciones.LimiteExcedidoEnColorException;
import modelo.prenda.Color;
import org.junit.Test;

import static org.junit.Assert.assertThat;
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

	@Test
	public void fromStringParseaCorrectamenteUnColorAPartirDeUna3Upla(){
		Color parsed = Color.fromString("(100,200,50");
		assertTrue(parsed.getRojo() == 100 && parsed.getVerde() == 200 && parsed.getAzul() == 50);
	}

	@Test
	public void fromStrinNoEsSensibleALosEspaciosEntreLosNumerosDeLa3Upla(){
		Color parsed = Color.fromString("	(	 100,  200, 60  ");
		assertTrue(parsed.getRojo() == 100 && parsed.getVerde() == 200 && parsed.getAzul() == 60);
	}
}
