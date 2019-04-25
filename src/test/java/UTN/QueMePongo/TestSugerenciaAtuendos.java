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
	public void prendasSuficientesParaSugerenciaConPosibleAccesorio() {
		guardarropa.addPrenda(remera);
		guardarropa.addPrenda(pantalon);
		guardarropa.addPrenda(zapatos);
		guardarropa.addPrenda(reloj);

		List<Atuendo> sugerencias = guardarropa.obtenerSugerencias();

		assertEquals(sugerencias.size(), 2);

		Atuendo atuendoSugeridoConAccesorio = sugerencias.get(0);
		Atuendo atuendoSugeridoSinAccesorio = sugerencias.get(1);

		assertEquals(atuendoSugeridoConAccesorio.getParteSuperior(), remera);
		assertEquals(atuendoSugeridoConAccesorio.getParteInferior(), pantalon);
		assertEquals(atuendoSugeridoConAccesorio.getCalzado(), zapatos);
		assertEquals(atuendoSugeridoConAccesorio.getAccesorio(), reloj);

		assertEquals(atuendoSugeridoSinAccesorio.getParteSuperior(), remera);
		assertEquals(atuendoSugeridoSinAccesorio.getParteInferior(), pantalon);
		assertEquals(atuendoSugeridoSinAccesorio.getCalzado(), zapatos);
		assertEquals(atuendoSugeridoSinAccesorio.getAccesorio(), null);
	}

	@Test
	public void prendasSuficientesParaSugerenciaSinAccesorio() {
		guardarropa.addPrenda(remera);
		guardarropa.addPrenda(pantalon);
		guardarropa.addPrenda(zapatos);

		List<Atuendo> sugerencias = guardarropa.obtenerSugerencias();

		assertEquals(sugerencias.size(), 1);

		Atuendo atuendoSugerido = sugerencias.get(0);

		assertEquals(atuendoSugerido.getParteSuperior(), remera);
		assertEquals(atuendoSugerido.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido.getCalzado(), zapatos);
		assertEquals(atuendoSugerido.getAccesorio(), null);
	}

	@Test
	public void prendasSuficientesParaMasDeUnaSugerenciaConPosibleAccesorio() {
		guardarropa.addPrenda(remera);
		guardarropa.addPrenda(camisa);
		guardarropa.addPrenda(pantalon);
		guardarropa.addPrenda(zapatos);
		guardarropa.addPrenda(reloj);

		List<Atuendo> sugerencias = guardarropa.obtenerSugerencias();

		assertEquals(sugerencias.size(), 4);

		Atuendo atuendoSugerido1ConAccesorio = sugerencias.get(0);
		Atuendo atuendoSugerido2ConAccesorio = sugerencias.get(1);
		Atuendo atuendoSugerido1SinAccesorio = sugerencias.get(2);
		Atuendo atuendoSugerido2SinAccesorio = sugerencias.get(3);

		assertEquals(atuendoSugerido1ConAccesorio.getParteSuperior(), remera);
		assertEquals(atuendoSugerido1ConAccesorio.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido1ConAccesorio.getCalzado(), zapatos);
		assertEquals(atuendoSugerido1ConAccesorio.getAccesorio(), reloj);
		
		assertEquals(atuendoSugerido2ConAccesorio.getParteSuperior(), camisa);
		assertEquals(atuendoSugerido2ConAccesorio.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido2ConAccesorio.getCalzado(), zapatos);
		assertEquals(atuendoSugerido2ConAccesorio.getAccesorio(), reloj);

		assertEquals(atuendoSugerido1SinAccesorio.getParteSuperior(), remera);
		assertEquals(atuendoSugerido1SinAccesorio.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido1SinAccesorio.getCalzado(), zapatos);
		assertEquals(atuendoSugerido1SinAccesorio.getAccesorio(), null);

		assertEquals(atuendoSugerido2SinAccesorio.getParteSuperior(), camisa);
		assertEquals(atuendoSugerido2SinAccesorio.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido2SinAccesorio.getCalzado(), zapatos);
		assertEquals(atuendoSugerido2SinAccesorio.getAccesorio(), null);
	}
}
