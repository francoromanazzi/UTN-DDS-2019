package repositorios;

import excepciones.EventoNoEncontradoException;
import modelo.evento.Evento;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.util.List;

public class RepositorioEventos implements WithGlobalEntityManager, TransactionalOps {
	public Evento buscarPorId(long id) throws EventoNoEncontradoException {
		Evento ret = entityManager().find(Evento.class, id);

		if (ret == null) throw new EventoNoEncontradoException();

		return ret;
	}

	@SuppressWarnings("unchecked")
	public List<Evento> obtenerEventosDeUsuario(Long id_user) {
		/*return entityManager()
				.createQuery("SELECT e FROM Usuario u JOIN u.eventos e " +
						"WHERE u.Id = :idUsuario", Evento.class).
						setParameter("idUsuario", id_user)
				.getResultList(); */
		return (List<Evento>) entityManager()
				.createNativeQuery("SELECT * FROM eventos e WHERE e.usuario_id = :idUsuario", Evento.class)
				.setParameter("idUsuario", id_user)
				.getResultList();
	}

	public void guardar(Evento evento) {
		withTransaction(() -> entityManager().persist(evento));
	}
}
