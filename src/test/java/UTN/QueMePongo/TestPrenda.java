package UTN.QueMePongo;

import excepciones.ColoresIgualesException;
import excepciones.MaterialNoTieneSentidoParaEseTipoException;
import excepciones.TipoNoPuedeSerNuloException;
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

	@Test(expected = ColoresIgualesException.class)
	public void exceptionSiColoresPrincipalYSecundarioSonIguales() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(128, 128, 128), Optional.of(new Color(128, 128, 128)));
	}

	@Test(expected = MaterialNoTieneSentidoParaEseTipoException.class)
	public void ExceptionSiTipoYMaterialSonIncompatibles() {
		new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ORO, new Color(0, 0, 0), Optional.empty());
	}

}
