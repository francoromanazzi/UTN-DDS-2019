package UTN.QueMePongo;

import modelo.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestUsuario {
    Usuario federico;
    Guardarropa guardarropaFederico;

    @Before
    public void crearUsuarioYGuardarropa(){
        federico = new Usuario();
        guardarropaFederico = new Guardarropa();
    }

    @Test
    public void agregarGuardarropaYVerificarQueUsuarioLoTenga(){
        federico.addGuardarropa(guardarropaFederico);
        assertTrue(federico.tieneGuardarropa(guardarropaFederico));
    }

    @Test
    public void removerGuardarropaYVerificarQueUsuarioYaNoLoTengaMas(){
    	federico.addGuardarropa(guardarropaFederico);
        assertTrue(federico.tieneGuardarropa(guardarropaFederico));
        federico.removeGuardarropa(guardarropaFederico);
        assertFalse(federico.tieneGuardarropa(guardarropaFederico));
    }
}
