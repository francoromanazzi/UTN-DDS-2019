package UTN.QueMePongo.Persistencia;

import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import modelo.usuario.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import servicios.UsuarioService;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TestPersistenciaGuardarropa extends AbstractPersistenceTest implements WithGlobalEntityManager {
    private Guardarropa guardarropa;
    private Prenda prenda1;
    private Prenda prenda2;
    private Usuario user;
    @Before
    public void crearGuardarropa(){
        prenda1 = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON,new Color(100,100,100), Optional.empty(), Optional.empty());
        prenda2 = new Prenda(Tipo.BOTAS, Material.CUERO,new Color(0,0,0), Optional.empty(), Optional.empty());
        guardarropa = new Guardarropa();
        guardarropa.addPrenda(prenda1);
        guardarropa.addPrenda(prenda2);
        user = new Usuario("Matias","elMati@gmail.com","1145322466","mati22","1234");
    }
    @Test
    public void persistirGuardarropa(){
        UsuarioService.persistir(user);
        entityManager().persist(prenda1);
        entityManager().persist(prenda2);
        entityManager().persist(guardarropa);

        List<Guardarropa> guardarropas = entityManager().
                createQuery("from Guardarropa", Guardarropa.class).
                getResultList();

        assertEquals(guardarropas.get(0).cantidadPrendas(),2);
        
    }
}
