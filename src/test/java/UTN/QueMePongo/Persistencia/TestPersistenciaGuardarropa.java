package UTN.QueMePongo.Persistencia;

import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import modelo.usuario.Usuario;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import servicios.Session;
import servicios.UsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TestPersistenciaGuardarropa extends AbstractPersistenceTest implements WithGlobalEntityManager {
    private Guardarropa guardarropa1 = new Guardarropa();
    private Guardarropa guardarropa2 = new Guardarropa();
    private Prenda prenda1 = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON,new Color(100,100,100), Optional.empty(), Optional.empty());;
    private Prenda prenda2 = new Prenda(Tipo.BOTAS, Material.CUERO,new Color(0,0,0), Optional.empty(), Optional.empty());
    private Usuario user = new Usuario("matias","mati@gmail.com","1145734639","mati543","1234");;
    
    @Test
    public void persistirGuardarropa(){
        guardarropa1.addPrenda(prenda1);
        guardarropa1.addPrenda(prenda2);
        entityManager().persist(guardarropa1);

        List<Guardarropa> guardarropas = entityManager().
                createQuery("from Guardarropa", Guardarropa.class).
                getResultList();

        assertEquals(guardarropas.get(0).cantidadPrendas(), 2);
        
        entityManager().remove(guardarropa1);
    }

    @Test
    public void persistirUsuarioConDosGuardarropas(){
        guardarropa1.addUsuario(user);
        guardarropa2.addUsuario(user);
        entityManager().persist(guardarropa1);
        entityManager().persist(guardarropa2);

        List<Guardarropa> guardarropas = entityManager().
                createQuery("from Guardarropa", Guardarropa.class).
                getResultList();

        assertEquals(guardarropas.get(0).getUsuariosPropietarios().size(), 1);
        assertEquals(guardarropas.get(1).getUsuariosPropietarios().size(), 1);

        entityManager().remove(guardarropa1);
        entityManager().remove(guardarropa2);
    }
    
    @Test
    public void persistirYObtenerGuardarropasDeUnUsuario(){
    	entityManager().persist(user);
        guardarropa1.addUsuario(user);
        guardarropa2.addUsuario(user);
        entityManager().persist(guardarropa1);
        entityManager().persist(guardarropa2);
        
        List<Guardarropa> guardarropas = new UsuarioService().GetGuardarropasDeUsuarioPorId(user.getId());

        assertEquals(2, guardarropas.size());
    }
}
