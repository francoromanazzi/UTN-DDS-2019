package UTN.QueMePongo;

import modelo.*;
import org.junit.Before;
import org.junit.Test;
import excepciones.GuardarropaConMayorPrendasQueCapMaxException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Optional;

public class TestUsuario {
    UsuarioPremium userPremium;
    UsuarioGratuito userGratuito;
    Guardarropa guardarropaGratuito;
    Guardarropa guardarropaPremium;

    @Before
    public void crearUsuarioYGuardarropa(){
    	userPremium = new UsuarioPremium();
    	userGratuito = new UsuarioGratuito(2);
    	guardarropaGratuito = new Guardarropa();
    	guardarropaPremium = new Guardarropa();
    }

    @Test
    public void agregarGuardarropaYVerificarQueUsuarioLoTenga(){
    	userPremium.addGuardarropa(guardarropaPremium);
        assertTrue(userPremium.tieneGuardarropa(guardarropaPremium));
    }

    @Test
    public void removerGuardarropaYVerificarQueUsuarioYaNoLoTengaMas(){
    	userPremium.addGuardarropa(guardarropaPremium);
        assertTrue(userPremium.tieneGuardarropa(guardarropaPremium));
        userPremium.removeGuardarropa(guardarropaPremium);
        assertFalse(userPremium.tieneGuardarropa(guardarropaPremium));
    }
    
    @Test(expected = GuardarropaConMayorPrendasQueCapMaxException.class)
    public void agregarAlGuardarropaGratuitoMasPrendasDeLasQueSoportarioYQueNoSePuedaAgregarAlUsuario(){
    	guardarropaGratuito.addPrenda(new Prenda(Tipo.REMERA, Material.ALGODON, new Color(255, 45, 0), Optional.empty()));
    	guardarropaGratuito.addPrenda(new Prenda(Tipo.REMERA, Material.ALGODON, new Color(0, 0, 42), Optional.empty()));
    	guardarropaGratuito.addPrenda(new Prenda(Tipo.REMERA, Material.ALGODON, new Color(244, 5, 22), Optional.empty()));
    	userGratuito.addGuardarropa(guardarropaGratuito);
    }
}
