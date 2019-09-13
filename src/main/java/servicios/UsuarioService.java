package servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import modelo.guardarropa.Guardarropa;
import modelo.usuario.Usuario;

public class UsuarioService{
	
	public static void eliminar(Usuario user) {
        try {
            Session.beginTransaction();
            Session.getEntityManager().remove(user);
            Session.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            Session.rollbackTransaction();
        }
    }

    public static void persistir(Usuario user) {
        try {
            Session.beginTransaction();
			String passHasheada = SHA256Builder.generarHash(user.getPassword());
			user.setPassword(passHasheada);
            Session.getEntityManager().persist(user);
            Session.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            Session.rollbackTransaction();
        }
    }

    public static void actualizar(Usuario user) {
        try {
            Session.beginTransaction();
            Session.getEntityManager().merge(user);
            Session.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            Session.rollbackTransaction();
        }
    }
	
	public static Usuario getUsuarioById(Long Id) {
		return Session.getEntityManager().find(Usuario.class, Id);
	}
	
	public static Usuario getUsuarioByCredentials(String username, String password)
    {
		Usuario user = null; 
		try{
			Query query = Session.getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.username = :nomUsuario and u.password = :pass");
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
	public static List<Usuario> GetAllUsuarios() {
		return (List<Usuario>) Session.getEntityManager().createQuery("FROM Usuario").getResultList();
	}
	
	public static List<Guardarropa> GetGuardarropasDeUsuarioPorId(Long id) {
		/*guardarropas = Session.getEntityManager().createQuery("SELECT g FROM Guardarropa g JOIN FETCH " +
													 		  "g.usuariosPropietarios u WHERE u.Id = :idUsuario",
												  Guardarropa.class).
												  setParameter("idUsuario", id).getResultList();*/
		
		List<Guardarropa> guardarropas = new ArrayList<Guardarropa>(); 
		guardarropas = Session.getEntityManager().
				createQuery("from Guardarropa", Guardarropa.class).
				getResultList();
		
		guardarropas = (List<Guardarropa>) guardarropas.stream().filter(
			g -> g.getUsuariosPropietarios().stream().anyMatch(u -> u.getId() == id)
		).collect(Collectors.toList());
		
		return guardarropas;
	}
}
