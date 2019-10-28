package server;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class ReseteadorEntityManager implements WithGlobalEntityManager {

	public void cerrar() {
		entityManager().close();
	}
}
