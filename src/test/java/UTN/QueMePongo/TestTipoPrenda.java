package UTN.QueMePongo;

import modelo.Categoria;
import modelo.Material;
import modelo.Tipo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTipoPrenda {

	@Test
	public void verificarTiposConCategorias() {
		assertEquals(Tipo.REMERA.getCategoria(), Categoria.SUPERIOR);
		assertEquals(Tipo.PANTALON.getCategoria(), Categoria.INFERIOR);
		assertEquals(Tipo.OJOTAS.getCategoria(), Categoria.CALZADO);
		assertEquals(Tipo.RELOJ.getCategoria(), Categoria.ACCESORIO);
	}

	@Test
	public void verificarTiposConMaterialesPosibles() {
		assertTrue(Tipo.REMERA.puedeSerDeMaterial(Material.ALGODON));
		assertTrue(Tipo.PANTALON.puedeSerDeMaterial(Material.ALGODON));
		assertTrue(Tipo.OJOTAS.puedeSerDeMaterial(Material.GOMA));
		assertTrue(Tipo.RELOJ.puedeSerDeMaterial(Material.PLATA));
	}
}
