package utils;

import excepciones.DesencriptacionException;
import excepciones.EventoNoEncontradoException;
import excepciones.GuardarropaNoEncontradoException;
import excepciones.UsuarioNoEncontradoException;
import modelo.evento.Evento;
import modelo.guardarropa.Guardarropa;
import modelo.usuario.Usuario;
import repositorios.RepositorioEventos;
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
		Usuario usuario = new RepositorioUsuarios().buscarPorId(idUser);

		if(!guardarropa.tieneUsuario(usuario)) {
			res.redirect("/error");
		}
	}

	public static void userEsPropietarioDeEvento(Request req, Response res) throws EventoNoEncontradoException, UsuarioNoEncontradoException {
		long idEvento = Long.parseLong(req.params("id"));
		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));

		Evento evento = new RepositorioEventos().buscarPorId(idEvento);
		Usuario usuario = new RepositorioUsuarios().buscarPorId(idUser);

		if(!usuario.getEventos().contains(evento)) {
			res.redirect("/error");
		}
	}
}
