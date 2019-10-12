package repositorios;

import modelo.sugerencia.Sugerencia;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.util.List;

public class RepositorioSugerencias implements WithGlobalEntityManager, TransactionalOps {
	public void guardarTodas(List<Sugerencia> sugerencias) {
		this.beginTransaction();
		sugerencias.forEach(sug -> entityManager().persist(sug));
		this.commitTransaction();
	}
}
