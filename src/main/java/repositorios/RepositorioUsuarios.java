package repositorios;

import excepciones.UsernameEnUsoException;
import excepciones.UsuarioNoEncontradoException;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import utils.SHA256Builder;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class RepositorioUsuarios implements WithGlobalEntityManager, TransactionalOps {
	public Usuario buscarPorId(Long Id) throws UsuarioNoEncontradoException {
		Usuario ret = entityManager().find(Usuario.class, Id);

		if(ret == null) throw new UsuarioNoEncontradoException();

		return ret;
	}

	public Usuario buscarPorCredenciales(String username, String pwdSinHash) throws UsuarioNoEncontradoException {
		try {
			Query query = entityManager().createQuery("SELECT u FROM Usuario u WHERE u.username = :nomUsuario AND u.password = :pass");
			query.setParameter("nomUsuario", username);
			query.setParameter("pass", SHA256Builder.generarHash(pwdSinHash));
			query.setMaxResults(1);

			Usuario ret = (Usuario) query.getSingleResult();

			if(ret == null) throw new UsuarioNoEncontradoException();

			return ret;
		}
		catch(NoResultException ex) {
			throw new UsuarioNoEncontradoException();
		}
	}

	public List<Usuario> obtenerTodos() {
		return entityManager().createQuery("FROM Usuario", Usuario.class).getResultList();
	}

	public void guardar(Usuario user) throws UsernameEnUsoException {
		if(this.obtenerTodos().stream().anyMatch(u -> u.getUsername().equals(user.getUsername())))
			throw new UsernameEnUsoException();

		withTransaction(() -> entityManager().persist(user));
	}
}
