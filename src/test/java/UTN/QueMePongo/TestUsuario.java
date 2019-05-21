package UTN.QueMePongo;

import excepciones.GuardarropaConMayorPrendasQueCapMaxException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import modelo.usuario.Gratuito;
import modelo.usuario.Premium;
import modelo.usuario.PrivilegiosUsuario;
import modelo.usuario.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestUsuario {
	private final Usuario userGratuito = new Usuario();
	private final Usuario userPremium = new Usuario();
    private final PrivilegiosUsuario privilegiosPremium = new Premium();
	private final PrivilegiosUsuario privilegiosGratuito = new Gratuito(2);
    private final Guardarropa guardarropa = new Guardarropa();

    @Before
	public void asignarPrivilegios() {
		userGratuito.setPrivilegios(privilegiosGratuito);
    	userPremium.setPrivilegios(privilegiosPremium);
	}

    @Test
    public void agregarGuardarropaVacioAUserGratuitoYVerificarQueUsuarioLoTenga(){
		userGratuito.addGuardarropa(guardarropa);
        assertTrue(userGratuito.tieneGuardarropa(guardarropa));
    }

    @Test
    public void removerGuardarropaVacioAUserGratuitoYVerificarQueUsuarioYaNoLoTengaMas(){
		userGratuito.addGuardarropa(guardarropa);
        assertTrue(userGratuito.tieneGuardarropa(guardarropa));
		userGratuito.removeGuardarropa(guardarropa);
        assertFalse(userGratuito.tieneGuardarropa(guardarropa));
    }
    
    @Test(expected = GuardarropaConMayorPrendasQueCapMaxException.class)
    public void agregarAlGuardarropaMasPrendasDeLasQueSoportariaYQueNoSePuedaAgregarAlUsuarioGratuito(){
		guardarropa.addPrenda(new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(255, 45, 0), Optional.empty()), userPremium);
		guardarropa.addPrenda(new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 42), Optional.empty()), userPremium);
		guardarropa.addPrenda(new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(244, 5, 22), Optional.empty()), userPremium);
		userGratuito.addGuardarropa(guardarropa);
    }
}
