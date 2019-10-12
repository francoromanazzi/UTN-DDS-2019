package utils;

import excepciones.DesencriptacionException;
import excepciones.GuardarropaNoEncontradoException;
import excepciones.UsuarioNoEncontradoException;
import modelo.guardarropa.Guardarropa;
import repositorios.RepositorioGuardarropas;
import repositorios.RepositorioUsuarios;
import spark.Request;
import spark.Response;

public final class Auth {

	public static void tieneToken(Request req, Response res) {
		try {
			if (Token.Desencriptar(req.cookie("userId")) == null)
				res.redirect("/");
		} catch (DesencriptacionException ex) {
			res.redirect("/error");
		}
	}

	public static void userEsPropietarioDeGuardarropa(Request req, Response res) throws GuardarropaNoEncontradoException, UsuarioNoEncontradoException {
		long idGuardarropa = Long.parseLong(req.params("id"));
		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));

		Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(idGuardarropa);

		if(!guardarropa.tieneUsuario(new RepositorioUsuarios().buscarPorId(idUser))) {
			res.redirect("/error");
		}
	}
}
