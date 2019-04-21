package UTN.QueMePongo;

import modelo.Guardarropa;
import modelo.Color;
import modelo.Material;
import modelo.Prenda;
import modelo.Tipo;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TestGuardarropa {

	Guardarropa guardarropa = new Guardarropa();
	Prenda remera = new Prenda(Tipo.REMERA, Material.ALGODON, new Color(255, 45, 0), Optional.empty());
	Prenda pantalon = new Prenda(Tipo.PANTALON, Material.ALGODON, new Color(254, 45, 0), Optional.of(new Color(128, 128, 128)));

	@Before
	public void iniciar() {

	}

	@Test
	public void agregarPrenda() {
		guardarropa.addPrenda(remera);
		guardarropa.addPrenda(pantalon);
		assertEquals(guardarropa.getPrendasSuperiores().size(), 1);
		assertEquals(guardarropa.getPrendasInferiores().size(), 1);
		assertEquals(guardarropa.getCalzados().size(), 0);
		assertEquals(guardarropa.getAccesorios().size(), 0);
	}

	@Test
	public void removerPrenda() {
		guardarropa.addPrenda(remera);
		guardarropa.removePrenda(remera);
		assertEquals(guardarropa.getPrendasSuperiores().size(), 0);
	}
}
