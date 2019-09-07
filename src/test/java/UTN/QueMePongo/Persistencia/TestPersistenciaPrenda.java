package UTN.QueMePongo.Persistencia;

import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;

public class TestPersistenciaPrenda extends AbstractPersistenceTest implements WithGlobalEntityManager {
    Prenda buzoRojo;
    @Before
    public void crearPrenda(){
        Color rojo = new Color(100,0,0);
        buzoRojo = new Prenda(Tipo.BUZO, Material.ALGODON,rojo, Optional.empty(), Optional.empty());
    }
    @Test
    public void persistirPrenda(){
        entityManager().persist(buzoRojo);

        List<Prenda> prendas = entityManager().
                createQuery("from Prenda", Prenda.class).
                getResultList();

        assertEquals(prendas.get(0).getTipo(),Tipo.BUZO);

    }

}
