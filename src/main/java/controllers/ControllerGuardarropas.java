package controllers;

import excepciones.GuardarropaNoEncontradoException;
import excepciones.UsernameNoExisteException;
import excepciones.UsernamePropietarioNoEncontradoException;
import excepciones.UsuarioNoEncontradoException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Material;
import modelo.prenda.Tipo;
import modelo.usuario.Usuario;
import org.uqbar.commons.model.Repo;
import repositorios.RepositorioGuardarropas;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Token;

import java.util.*;
import java.util.stream.Collectors;

public class ControllerGuardarropas {

	public static ModelAndView listar(Request req, Response res) {
		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));
		List<Guardarropa> listaDeGuardarropas = new RepositorioGuardarropas().obtenerTodosDelUsuario(idUser);
		return new ModelAndView(listaDeGuardarropas, "guardarropas/index.hbs");
	}

	public static ModelAndView crear(Request req, Response res) {
		return new ModelAndView(null, "guardarropas/nuevo.hbs");
	}

	public static String registrarGuardarropaNuevo(Request req, Response res) throws UsernamePropietarioNoEncontradoException {
		RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
		Guardarropa guardarropa = new Guardarropa();

		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));
		Usuario userCreador = repositorioUsuarios.buscarPorId(idUser);
		guardarropa.addUsuario(userCreador);

		String usuariosPropietarios = req.queryParams("usuarios-propietarios");

		if(!usuariosPropietarios.equals("")) {
			Arrays.stream(usuariosPropietarios.split(","))
					.forEach(username -> {
						try {
							if (!username.equals(userCreador.getUsername()))
								guardarropa.addUsuario(repositorioUsuarios.buscarPorUsername(username));
						} catch (UsuarioNoEncontradoException ex) {
							throw new UsernamePropietarioNoEncontradoException();
						}
					});
		}

		new RepositorioGuardarropas().guardar(guardarropa);

		res.redirect("/guardarropas/" + guardarropa.getId() + "/prendas?guardarropaRecienCreado=1");
		return null;
	}

	public static void usernamePropietarioNoEncontrado(UsernamePropietarioNoEncontradoException ex, Request req, Response res) {
		res.redirect("/guardarropas/nuevo?usernamePropietarioNoEncontrado=1");
	}

	public static void noEncontrado(GuardarropaNoEncontradoException ex, Request req, Response res) {
		res.redirect("/error");
	}
}
