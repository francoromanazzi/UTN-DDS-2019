package UTN.QueMePongo;

import modelo.prenda.Categoria;
import modelo.prenda.Material;
import modelo.prenda.Tipo;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestTipoPrenda {

	@Test
	public void verificarTiposConCategorias() {
		assertEquals(Tipo.REMERA_MANGA_CORTA.getCategoria(), Categoria.SUPERIOR);
		assertEquals(Tipo.PANTALON_LARGO.getCategoria(), Categoria.INFERIOR);
		assertEquals(Tipo.OJOTAS.getCategoria(), Categoria.CALZADO);
		assertEquals(Tipo.RELOJ.getCategoria(), Categoria.ACCESORIO);
	}

	@Test
	public void verificarTiposConMaterialesPosibles() {
		assertTrue(Tipo.REMERA_MANGA_CORTA.puedeSerDeMaterial(Material.ALGODON));
		assertTrue(Tipo.PANTALON_LARGO.puedeSerDeMaterial(Material.ALGODON));
		assertFalse(Tipo.OJOTAS.puedeSerDeMaterial(Material.CUERO));
		assertFalse(Tipo.RELOJ.puedeSerDeMaterial(Material.POLIESTER));
	}
}
