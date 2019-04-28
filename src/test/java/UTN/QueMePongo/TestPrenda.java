package UTN.QueMePongo;

import excepciones.ColoresIgualesException;
import excepciones.MaterialNoTieneSentidoParaEseTipoException;
import excepciones.TipoNoPuedeSerNuloException;
import modelo.Color;
import modelo.Material;
import modelo.Prenda;
import modelo.Tipo;
import org.junit.Test;

import java.util.Optional;

public class TestPrenda {

	@Test(expected = TipoNoPuedeSerNuloException.class)
	public void exceptionSiPrendaTieneTipoNulo() {
		new Prenda(null, Material.ALGODON, new Color(0, 0,  0), Optional.empty());
	}

	@Test(expected = ColoresIgualesException.class)
	public void exceptionSiColoresPrincipalYSecundarioSonIguales() {
		new Prenda(Tipo.REMERA, Material.ALGODON, new Color(128, 128, 128), Optional.of(new Color(128, 128, 128)));
	}

	@Test(expected = MaterialNoTieneSentidoParaEseTipoException.class)
	public void ExceptionSiTipoYMaterialSonIncompatibles() {
		new Prenda(Tipo.REMERA, Material.ORO, new Color(0, 0, 0), Optional.empty());
	}

}
