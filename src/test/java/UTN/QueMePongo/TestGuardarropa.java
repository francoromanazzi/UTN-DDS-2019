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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestGuardarropa {

	Guardarropa guardarropa = new Guardarropa();
	Prenda remera = new Prenda(Tipo.REMERA, Material.ALGODON, new Color(255, 45, 0), Optional.empty());

	@Test
	public void agregoPrendaYVerificoQueElGuardarropaLaTenga() {
		guardarropa.addPrenda(remera);
		assertTrue(guardarropa.tienePrenda(remera));
	}

	@Test
	public void removerPrendaYVerificoQueElGuardarropaYaNoLaTenga() {
		guardarropa.addPrenda(remera);
		assertTrue(guardarropa.tienePrenda(remera));
		guardarropa.removePrenda(remera);
		assertFalse(guardarropa.tienePrenda(remera));
	}
}
