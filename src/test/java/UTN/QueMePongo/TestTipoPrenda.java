package UTN.QueMePongo;

import junit.framework.Assert;
import modelo.Categoria;
import modelo.Tipo;
import org.junit.Test;

public class TestTipoPrenda {

    @Test
    public void verificarTiposConCategorias(){
        Assert.assertEquals(Tipo.REMERA.getCategoria(), Categoria.SUPERIOR);
        Assert.assertEquals(Tipo.PANTALON.getCategoria(), Categoria.INFERIOR);
        Assert.assertEquals(Tipo.OJOTAS.getCategoria(), Categoria.CALZADO);
        Assert.assertEquals(Tipo.RELOJ.getCategoria(), Categoria.ACCESORIO);
    }
}
