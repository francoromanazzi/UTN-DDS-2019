package servicios;

import modelo.guardarropa.Guardarropa;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class UsuarioService implements WithGlobalEntityManager {

	public Usuario getUsuarioById(Long Id) {
		return entityManager().find(Usuario.class, Id);
	}

	public Usuario getUsuarioByCredentials(String username, String pwdSinHash) throws NoResultException {
		Query query = entityManager().createQuery("SELECT u FROM Usuario u WHERE u.username = :nomUsuario and u.password = :pass");
		query.setParameter("nomUsuario", username);
		query.setParameter("pass", SHA256Builder.generarHash(pwdSinHash));
		query.setMaxResults(1);
		return (Usuario) query.getSingleResult();
	}

	public List<Usuario> getAllUsuarios() {
		return entityManager().createQuery("FROM Usuario", Usuario.class).getResultList();
	}

	public List<Guardarropa> getGuardarropasDeUsuarioPorId(Long id_user) {
		return entityManager().createQuery("SELECT g FROM Guardarropa g JOIN FETCH " +
				"g.usuariosPropietarios u WHERE u.Id = :idUsuario", Guardarropa.class).
				setParameter("idUsuario", id_user).getResultList();
	}
}
