package UTN.QueMePongo;

import excepciones.parametros_nulos.ColorSecundarioNoPuedeSerNuloException;
import excepciones.ColoresIgualesException;
import excepciones.MaterialNoTieneSentidoParaEseTipoException;
import excepciones.parametros_nulos.TipoNoPuedeSerNuloException;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import org.junit.Test;

import java.util.Optional;

public class TestPrenda {

	@Test(expected = TipoNoPuedeSerNuloException.class)
	public void exceptionSiPrendaTieneTipoNulo() {
		new Prenda(null, Material.ALGODON, new Color(0, 0,  0), Optional.empty());
	}

	@Test(expected = ColorSecundarioNoPuedeSerNuloException.class)
	public void exceptionSiColorSecundarioTieneTipoNulo() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0,  0), null);
	}

	@Test(expected = ColoresIgualesException.class)
	public void exceptionSiColoresPrincipalYSecundarioSonIguales() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(128, 128, 128), Optional.of(new Color(128, 128, 128)));
	}

	@Test(expected = MaterialNoTieneSentidoParaEseTipoException.class)
	public void exceptionSiTipoYMaterialSonIncompatibles() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ORO, new Color(0, 0, 0), Optional.empty());
	}
}
