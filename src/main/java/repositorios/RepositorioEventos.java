package repositorios;

import modelo.evento.Evento;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioEventos implements WithGlobalEntityManager {
	public List<Evento> obtenerEventosDeUsuario(Long id_user) {
		return entityManager()
				.createQuery("SELECT e FROM Usuario u JOIN FETCH u.eventos e " +
						"WHERE u.Id = :idUsuario", Evento.class).
						setParameter("idUsuario", id_user)
				.getResultList();
	}
}
