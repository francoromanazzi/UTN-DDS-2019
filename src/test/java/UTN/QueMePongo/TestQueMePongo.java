package UTN.QueMePongo;

import org.junit.Test;

import junit.framework.Assert;
import modelo.Atuendo;
import modelo.Categoria;
import modelo.Color;
import modelo.Material;
import modelo.Prenda;
import modelo.Tipo;
import excepciones.ColoresIgualesException;
import excepciones.PrendaNoPuedeIrAAtuendoException;

public class TestQueMePongo {	
	Prenda remera = new Prenda(Tipo.REMERA, Material.ALGODON, new Color(0, 0, 128), null);
	Prenda pantalon = new Prenda(Tipo.PANTALON, Material.ALGODON, new Color(0, 128, 128), null);
	Prenda zapatos = new Prenda(Tipo.ZAPATOS, Material.ALGODON, new Color(0, 0, 64), null);
	Prenda gorra = new Prenda(Tipo.GORRA, Material.POLIESTER, new Color(128, 0, 128), new Color(0, 0, 64));
	Prenda campera = new Prenda(Tipo.CAMPERA, Material.CUERO, new Color(64, 64, 64), new Color(128, 128, 128));
	Prenda ojotas = new Prenda(Tipo.OJOTAS, Material.ALGODON, new Color(128, 128, 128), null);
	
	Atuendo atuendo1 = new Atuendo();
	Atuendo atuendo2 = new Atuendo();

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
	
	@Test(expected = RuntimeException.class)
	public void prendaIncorrecta(){
		Prenda unaPrenda = new Prenda(null, null, null, null);	
	}
	
	@Test(expected = ColoresIgualesException.class)
	public void coloresIgualesDePrenda(){
		Prenda unaPrenda = new Prenda(Tipo.OJOTAS, Material.ALGODON, new Color(128, 128, 128), new Color(128, 128, 128));	
	}
	
	@Test
	public void verificarTiposConCategorias(){
		Assert.assertEquals(Tipo.REMERA.getCategoria(), Categoria.SUPERIOR);	
		Assert.assertEquals(Tipo.PANTALON.getCategoria(), Categoria.INFERIOR);
		Assert.assertEquals(Tipo.OJOTAS.getCategoria(), Categoria.CALZADO);
		Assert.assertEquals(Tipo.RELOJ.getCategoria(), Categoria.ACCESORIO);
	}
}
