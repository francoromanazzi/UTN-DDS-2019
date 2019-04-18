package UTN.QueMePongo;

import excepciones.PrendaNoPuedeIrAAtuendoException;
import modelo.*;
import org.junit.Before;
import org.junit.Test;

public class TestAtuendo {

    Atuendo atuendo1;
    Atuendo atuendo2;

    Prenda remera;
    Prenda pantalon;
    Prenda zapatos;
    Prenda gorra;
    Prenda campera;
    Prenda ojotas;


    @Before
    public void iniciar(){
        atuendo1 = new Atuendo();
        atuendo2 = new Atuendo();

        remera = new Prenda(Tipo.REMERA, Material.ALGODON, new Color(0, 0, 128), null);
        pantalon = new Prenda(Tipo.PANTALON, Material.ALGODON, new Color(0, 128, 128), null);
        zapatos = new Prenda(Tipo.ZAPATOS, Material.ALGODON, new Color(0, 0, 64), null);
        gorra = new Prenda(Tipo.GORRA, Material.POLIESTER, new Color(128, 0, 128), new Color(0, 0, 64));
        campera = new Prenda(Tipo.CAMPERA, Material.CUERO, new Color(64, 64, 64), new Color(128, 128, 128));
        ojotas = new Prenda(Tipo.OJOTAS, Material.ALGODON, new Color(128, 128, 128), null);
    }

    @Test
    public void atuendoCorrecto() {
        atuendo1.setAccesorio(gorra);
        atuendo1.setCalzado(ojotas);
        atuendo1.setParteInferior(pantalon);
        atuendo1.setParteSuperior(remera);
    }

    @Test(expected = PrendaNoPuedeIrAAtuendoException.class)
    public void atuendoIncorrecto(){
        atuendo1.setAccesorio(ojotas);
        atuendo1.setCalzado(gorra);
        atuendo1.setParteInferior(pantalon);
        atuendo1.setParteSuperior(remera);
    }
}
