package servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import modelo.guardarropa.Guardarropa;
import modelo.usuario.Usuario;

public class UsuarioService implements TransactionalOps, WithGlobalEntityManager {
	
	public void eliminar(Usuario user) {
        try {
            beginTransaction();
            entity().remove(user);
            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
        }
    }

	private EntityManager entity() {
		return this.entityManager();
	}

    public void persistir(Usuario user) {
        try {
            beginTransaction();
			String passHasheada = SHA256Builder.generarHash(user.getPassword());
			user.setPassword(passHasheada);
            entity().persist(user);
            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
        }
    }

	private static void rollback() {
		Session.rollbackTransaction();
	}

    public void actualizar(Usuario user) {
        try {
            beginTransaction();
            entity().merge(user);
            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
        }
    }
	
	public Usuario getUsuarioById(Long Id) {
		return entity().find(Usuario.class, Id);
	}
	
	public Usuario getUsuarioByCredentials(String username, String password)
    {
		Usuario user = null; 
		try{
			Query query = entity().createQuery("SELECT u FROM Usuario u WHERE u.username = :nomUsuario and u.password = :pass");
			query.setParameter("nomUsuario", username);
			query.setParameter("pass", SHA256Builder.generarHash(password));
			query.setMaxResults(1);
			user = (Usuario) query.getSingleResult();
		}
		catch(NoResultException e){
			e.printStackTrace();
		}
		return user;
    }
	
	@SuppressWarnings("unchecked")
	public List<Usuario> GetAllUsuarios() {
		return (List<Usuario>) entity().createQuery("FROM Usuario").getResultList();
	}
	
	public List<Guardarropa> GetGuardarropasDeUsuarioPorId(Long id) {
		/*guardarropas = Session.getEntityManager().createQuery("SELECT g FROM Guardarropa g JOIN FETCH " +
													 		  "g.usuariosPropietarios u WHERE u.Id = :idUsuario",
												  Guardarropa.class).
												  setParameter("idUsuario", id).getResultList();*/
		
		
		// NO FUNCIONA
//		List<Long> id_guardarropas =
//				Session.getEntityManager()
//				.createQuery("SELECT gu.guardarropa_id FROM guardarropas_usuarios AS gu WHERE gu.usuario_id = " + id, Long.class)
//				.getResultList();
//		
//		List<Guardarropa> guardarropas =
//				id_guardarropas
//				.stream()
//				.map(id_g -> {
//					return Session
//							.getEntityManager()
//							.createQuery("FROM Guardarropa AS g WHERE g.Id = " + id_g, Guardarropa.class)
//							.getSingleResult();
//				}).collect(Collectors.toList());
//		
//		return guardarropas;
		
		
		
		// TAMPOCO FUNCIONA
		
		List<Guardarropa> guardarropas = new ArrayList<Guardarropa>(); 
		guardarropas = entity().
				createQuery("from Guardarropa", Guardarropa.class).
				getResultList();
		
		guardarropas = (List<Guardarropa>) guardarropas.stream().filter(
			g -> g.getUsuariosPropietarios().stream().anyMatch(u -> u.getId() == id)
		).collect(Collectors.toList());
		
		return guardarropas;
	}
}
