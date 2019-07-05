package repositorios;

import modelo.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {
	private static final RepositorioUsuarios ourInstance = new RepositorioUsuarios();

	public static RepositorioUsuarios getInstance() {
		return ourInstance;
	}

	private RepositorioUsuarios() {}

	public List<Usuario> getUsuarios() {
		return new ArrayList<>();
	}
}
