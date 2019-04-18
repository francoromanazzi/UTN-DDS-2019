package UTN.QueMePongo;

import modelo.Guardarropa;
import modelo.Color;
import modelo.Material;
import modelo.Prenda;
import modelo.Tipo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGuardarropa {

    Guardarropa guardarropa = new Guardarropa();

    @Test
    public void agregarPrenda(){
        guardarropa.addPrenda(new Prenda(Tipo.REMERA, Material.ALGODON, new Color(254, 45, 0), null));
        guardarropa.addPrenda(new Prenda(Tipo.PANTALON, Material.ALGODON, new Color(254, 45, 0), new Color(128, 128, 128)));
        assertEquals(guardarropa.getPrendas().size(),2);
    }

    @Test
    public void removerPrenda(){
        Prenda remera = new Prenda(Tipo.REMERA, Material.ALGODON, new Color(254, 45, 0), null);
        Prenda pantalon = new Prenda(Tipo.PANTALON, Material.ALGODON, new Color(128, 128, 128), null);
        guardarropa.addPrenda(remera);
        guardarropa.addPrenda(pantalon);
        guardarropa.removePrenda(remera);
        assertEquals(guardarropa.getPrendas().size(),1);
    }
}
