package UTN.QueMePongo;

import excepciones.CapacidadExcedidaGuardarropaException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import modelo.usuario.Gratuito;
import modelo.usuario.Premium;
import modelo.usuario.PrivilegioUsuario;
import modelo.usuario.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestGuardarropa {
	private final Usuario userGratuito = new Usuario("", "", "", "", "");
	private final Usuario userPremium = new Usuario("", "", "", "", "");
	private final PrivilegioUsuario privilegiosPremium = new Premium();
	private final PrivilegioUsuario privilegiosGratuito = new Gratuito(1);
	private final Guardarropa guardarropa = new Guardarropa();
	private final Prenda remera = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(255, 45, 0), Optional.empty(), Optional.empty());
	private final Prenda remera2 = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.POLIESTER, new Color(255, 45, 0), Optional.empty(), Optional.empty());

	@Before
	public void asignarPrivilegios() {
		userGratuito.setPrivilegio(privilegiosGratuito);
		userPremium.setPrivilegio(privilegiosPremium);
	}

	@Before
	public void asignoLosGuardarropas() {
		userGratuito.setPrivilegio(privilegiosGratuito);
		userPremium.setPrivilegio(privilegiosPremium);
	}

	@Test
	public void agregoPrendaYVerificoQueElGuardarropaLaTenga() {
		userPremium.addGuardarropa(guardarropa);
		userPremium.addPrenda(remera, guardarropa);
		assertTrue(guardarropa.tienePrenda(remera));
	}

	@Test
	public void removerPrendaYVerificoQueElGuardarropaYaNoLaTenga() {
		userPremium.addGuardarropa(guardarropa);
		userPremium.addPrenda(remera, guardarropa);
		assertTrue(guardarropa.tienePrenda(remera));
		guardarropa.removePrenda(remera);
		assertFalse(guardarropa.tienePrenda(remera));
	}

	@Test(expected = CapacidadExcedidaGuardarropaException.class)
	public void guardarropaSuperaElLimiteQueTieneDePrendasPosiblesParaUsuarioGratuito() {
		userGratuito.addGuardarropa(guardarropa);
		userGratuito.addPrenda(remera, guardarropa);
		userGratuito.addPrenda(remera2, guardarropa);
	}
}
