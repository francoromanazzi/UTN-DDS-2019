package servicios;

import java.util.List;

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
            rollbackTransaction();
            rollbackTransaction();
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
            rollbackTransaction();
        }
    }

    public void actualizar(Usuario user) {
        try {
            beginTransaction();
            entity().merge(user);
            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction();
        }
    }
	
	public Usuario getUsuarioById(Long Id) {
		return entity().find(Usuario.class, Id);
	}

	public Usuario getUsuarioByCredentials(String username, String pwdSinHash) throws NoResultException {
		Query query = entity().createQuery("SELECT u FROM Usuario u WHERE u.username = :nomUsuario and u.password = :pass");
		query.setParameter("nomUsuario", username);
		query.setParameter("pass", SHA256Builder.generarHash(pwdSinHash));
		query.setMaxResults(1);
		return (Usuario) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> GetAllUsuarios() {
		return (List<Usuario>) entity().createQuery("FROM Usuario").getResultList();
	}
	
	public List<Guardarropa> GetGuardarropasDeUsuarioPorId(Long id) {
		return entity().createQuery("SELECT g FROM Guardarropa g JOIN FETCH " +
									"g.usuariosPropietarios u WHERE u.Id = :idUsuario", Guardarropa.class).
						setParameter("idUsuario", id).getResultList();
	}
}
