package UTN.QueMePongo;

import modelo.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

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
        guardarropaRodrigo.addPrenda(new Prenda(Tipo.REMERA, Material.ALGODON, new Color(254, 45, 0), Optional.empty()));
        guardarropaRodrigo.addPrenda(new Prenda(Tipo.CAMISA, Material.ALGODON, new Color(150, 45, 40), Optional.empty()));
        guardarropaRodrigo.addPrenda(new Prenda(Tipo.PANTALON, Material.ALGODON, new Color(254, 45, 0), Optional.of(new Color(128, 128, 128))));
        guardarropaRodrigo.addPrenda(new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(254,45,0), Optional.empty() ));
        rodrigo.addGuardarropa(guardarropaRodrigo);

        federico = new Usuario();
        guardarropaFederico = new Guardarropa();
        guardarropaFederico.addPrenda(new Prenda(Tipo.REMERA, Material.ALGODON, new Color(254, 45, 0), Optional.empty()));
        guardarropaFederico.addPrenda(new Prenda(Tipo.CAMISA, Material.ALGODON, new Color(150, 45, 40), Optional.empty()));
        guardarropaFederico.addPrenda(new Prenda(Tipo.PANTALON, Material.ALGODON, new Color(254, 45, 0), Optional.of(new Color(128, 128, 128))));
        guardarropaFederico.addPrenda(new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(254,45,0), Optional.empty() ));
        federico.addGuardarropa(guardarropaFederico);
    }

    @Test
    public void agregarGuardarropa(){
        Guardarropa guardarropaFederico2 = new Guardarropa();
        guardarropaFederico2.addPrenda(new Prenda(Tipo.REMERA, Material.ALGODON, new Color(150, 45, 40), Optional.empty()));
        guardarropaFederico2.addPrenda(new Prenda(Tipo.GORRA, Material.POLIESTER, new Color(150, 45, 40), Optional.empty()));
        guardarropaFederico2.addPrenda(new Prenda(Tipo.PANTALON, Material.ALGODON, new Color(254, 45, 0), Optional.of(new Color(128, 128, 128))));
        guardarropaFederico2.addPrenda(new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(254,45,0), Optional.empty() ));
        federico.addGuardarropa(guardarropaFederico2);
        assertEquals(federico.getGuardarropas().size(),2);
    }

    @Test
    public void removerGuardarropa(){
        rodrigo.removeGuardarropa(guardarropaRodrigo);
        assertEquals(rodrigo.getGuardarropas().size(),0);
    }
}
