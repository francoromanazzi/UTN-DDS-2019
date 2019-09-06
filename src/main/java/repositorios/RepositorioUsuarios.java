package repositorios;

import modelo.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RepositorioUsuarios {
	private static RepositorioUsuarios ourInstance;
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
	private List<Usuario> usuarios;

	public static synchronized RepositorioUsuarios getInstance() {
		if(ourInstance == null)
			ourInstance = new RepositorioUsuarios();
		
		return ourInstance;
	}

	@SuppressWarnings("unchecked")
	private RepositorioUsuarios() {
		EntityManager manager = emf.createEntityManager();

		this.usuarios = (List<Usuario>)manager.createQuery("FROM Usuario").getResultList();
		
		manager.close();
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
}
