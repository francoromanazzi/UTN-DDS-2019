package UTN.QueMePongo;

import excepciones.ColoresIgualesException;
import modelo.Color;
import modelo.Material;
import modelo.Prenda;
import modelo.Tipo;
import org.junit.Before;
import org.junit.Test;

public class TestPrenda {

    @Test(expected = RuntimeException.class)
    public void prendaIncorrecta(){
        Prenda unaPrenda = new Prenda(null, null, null, null);
    }

    @Test(expected = ColoresIgualesException.class)
    public void coloresIgualesDePrenda(){
        Prenda unaPrenda = new Prenda(Tipo.OJOTAS, Material.ALGODON, new Color(128, 128, 128), new Color(128, 128, 128));
    }

}
