package UTN.QueMePongo;

import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import org.junit.Test;

import excepciones.CapacidadExcedidaGuardarropaException;
import java.util.Optional;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestGuardarropa {

	Guardarropa guardarropa = new Guardarropa();
	Prenda remera = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(255, 45, 0), Optional.empty());

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
	
	@Test(expected = CapacidadExcedidaGuardarropaException.class)
	public void guardarropaSuperaElLimiteQueTieneDePrendasPosibles() {
		guardarropa.setCapacidadMaxima(1);
		guardarropa.addPrenda(remera);
		guardarropa.addPrenda(remera);
	}
}
