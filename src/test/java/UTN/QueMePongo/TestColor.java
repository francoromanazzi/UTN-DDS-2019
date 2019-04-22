package UTN.QueMePongo;

import modelo.Color;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestColor {

	@Test(expected = IllegalArgumentException.class)
	public void rgbNoEstaEntre0Y255() {
		new Color(0, 0, 300);
	}

	@SuppressWarnings("null")
	@Test(expected = RuntimeException.class)
	public void rgbNulo() {
		new Color(0, 0, (Integer) null);
	}

	@Test
	public void coloresIguales() {
		assertTrue(new Color(0, 0, 0).esIgualA(new Color(0, 0, 0)));
	}
}
