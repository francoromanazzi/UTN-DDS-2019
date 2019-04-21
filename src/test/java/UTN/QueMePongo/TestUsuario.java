package UTN.QueMePongo;

import modelo.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUsuario {
    Usuario rodrigo;
    Usuario federico;
    Guardarropa guardarropaRodrigo;
    Guardarropa guardarropaFederico;

    @Before
    public void crearUsuario(){
        rodrigo = new Usuario();
        guardarropaRodrigo = new Guardarropa();
        guardarropaRodrigo.addPrenda(new Prenda(Tipo.REMERA, Material.ALGODON, new Color(254, 45, 0), null));
        guardarropaRodrigo.addPrenda(new Prenda(Tipo.CAMISA, Material.LANA, new Color(150, 45, 40), null));
        guardarropaRodrigo.addPrenda(new Prenda(Tipo.PANTALON, Material.ALGODON, new Color(254, 45, 0), new Color(128, 128, 128)));
        guardarropaRodrigo.addPrenda(new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(254,45,0), null ));
        rodrigo.addGuardarropa(guardarropaRodrigo);

        federico = new Usuario();
        guardarropaFederico = new Guardarropa();
        guardarropaFederico.addPrenda(new Prenda(Tipo.REMERA, Material.ALGODON, new Color(254, 45, 0), null));
        guardarropaFederico.addPrenda(new Prenda(Tipo.CAMISA, Material.LANA, new Color(150, 45, 40), null));
        guardarropaFederico.addPrenda(new Prenda(Tipo.PANTALON, Material.ALGODON, new Color(254, 45, 0), new Color(128, 128, 128)));
        guardarropaFederico.addPrenda(new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(254,45,0), null ));
        federico.addGuardarropa(guardarropaFederico);
    }

    @Test
    public void agregarGuardarropa(){
        federico.addGuardarropa(guardarropaFederico);
        Guardarropa guardarropaFederico2 = new Guardarropa();
        guardarropaFederico2.addPrenda(new Prenda(Tipo.REMERA, Material.LANA, new Color(150, 45, 40), null));
        guardarropaFederico2.addPrenda(new Prenda(Tipo.GORRA, Material.LANA, new Color(150, 45, 40), null));
        guardarropaFederico2.addPrenda(new Prenda(Tipo.PANTALON, Material.ALGODON, new Color(254, 45, 0), new Color(128, 128, 128)));
        guardarropaFederico2.addPrenda(new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(254,45,0), null ));
        assertEquals(federico.getGuardarropas().size(),2);
    }

    @Test
    public void removerGuardarropa(){
        rodrigo.removeGuardarropa(guardarropaRodrigo);
        assertEquals(rodrigo.getGuardarropas().size(),0);
    }
}
