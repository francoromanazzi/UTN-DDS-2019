package UTN.QueMePongo;

import modelo.Guardarropa;
import modelo.Atuendo;
import modelo.Color;
import modelo.Material;
import modelo.Prenda;
import modelo.Tipo;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TestGuardarropa {

	Guardarropa guardarropa = new Guardarropa();
	Prenda remera = new Prenda(Tipo.REMERA, Material.ALGODON, new Color(255, 45, 0), Optional.empty());
	Prenda pantalon = new Prenda(Tipo.PANTALON, Material.ALGODON, new Color(254, 45, 0), Optional.of(new Color(128, 128, 128)));
	Prenda reloj = new Prenda(Tipo.RELOJ, Material.ORO, new Color(128, 128, 128), Optional.of(new Color(64, 128, 64)));
	Prenda zapatos = new Prenda(Tipo.ZAPATOS, Material.CUERO, new Color(128, 128, 128), Optional.empty());

	@Test
	public void agregarPrenda() {
		guardarropa.addPrenda(remera);
		guardarropa.addPrenda(pantalon);
		guardarropa.addPrenda(zapatos);
		guardarropa.addPrenda(reloj);
		assertEquals(guardarropa.getPrendasSuperiores().size(), 1);
		assertEquals(guardarropa.getPrendasInferiores().size(), 1);
		assertEquals(guardarropa.getCalzados().size(), 1);
		assertEquals(guardarropa.getAccesorios().size(), 1);
	}

	@Test
	public void removerPrenda() {
		guardarropa.addPrenda(remera);
		assertEquals(guardarropa.getPrendasSuperiores().size(), 1);
		guardarropa.removePrenda(remera);
		assertEquals(guardarropa.getPrendasSuperiores().size(), 0);
	}
}
