package UTN.QueMePongo;

import modelo.*;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TestSugerenciaAtuendos {
	Guardarropa guardarropa = new Guardarropa();
	Prenda remera = new Prenda(Tipo.REMERA, Material.ALGODON, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	Prenda camisa = new Prenda(Tipo.CAMISA, Material.ALGODON, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	Prenda pantalon = new Prenda(Tipo.PANTALON, Material.DENIM, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	Prenda zapatos = new Prenda(Tipo.ZAPATOS, Material.CUERO, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	Prenda reloj = new Prenda(Tipo.RELOJ, Material.PLATA, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));

	@Test
	public void prendasInsuficientesParaSugerencia() {
		guardarropa.addPrenda(remera);
		assertEquals(guardarropa.obtenerSugerencias().size(), 0);
	}

	@Test
	public void prendasSuficientesParaSugerencia() {
		guardarropa.addPrenda(remera);
		guardarropa.addPrenda(pantalon);
		guardarropa.addPrenda(zapatos);
		guardarropa.addPrenda(reloj);

		List<Atuendo> sugerencias = guardarropa.obtenerSugerencias();

		assertEquals(sugerencias.size(), 1);

		Atuendo atuendoSugerido = sugerencias.get(0);

		assertEquals(atuendoSugerido.getParteSuperior(), remera);
		assertEquals(atuendoSugerido.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido.getCalzado(), zapatos);
		assertEquals(atuendoSugerido.getAccesorio(), reloj);
	}

	@Test
	public void prendasSuficientesParaMasDeUnaSugerencia() {
		guardarropa.addPrenda(remera);
		guardarropa.addPrenda(camisa);
		guardarropa.addPrenda(pantalon);
		guardarropa.addPrenda(zapatos);
		guardarropa.addPrenda(reloj);

		List<Atuendo> sugerencias = guardarropa.obtenerSugerencias();
		Atuendo atuendoSugerido1 = sugerencias.get(0);
		Atuendo atuendoSugerido2 = sugerencias.get(1);

		assertEquals(sugerencias.size(), 2);
		
		assertEquals(atuendoSugerido1.getParteSuperior(), remera);
		assertEquals(atuendoSugerido1.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido1.getCalzado(), zapatos);
		assertEquals(atuendoSugerido1.getAccesorio(), reloj);
		
		assertEquals(atuendoSugerido2.getParteSuperior(), camisa);
		assertEquals(atuendoSugerido2.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido2.getCalzado(), zapatos);
		assertEquals(atuendoSugerido2.getAccesorio(), reloj);
	}
}
