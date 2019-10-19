package controllers;

import excepciones.GuardarropaNoEncontradoException;
import modelo.guardarropa.Guardarropa;
import repositorios.RepositorioGuardarropas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Token;

import java.util.List;

public class ControllerGuardarropas {

	public static ModelAndView listar(Request req, Response res) {
		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));
		List<Guardarropa> listaDeGuardarropas = new RepositorioGuardarropas().obtenerTodosDelUsuario(idUser);
		return new ModelAndView(listaDeGuardarropas, "guardarropas/index.hbs");
	}

	public static void noEncontrado(GuardarropaNoEncontradoException ex, Request req, Response res) {
		res.redirect("/error");
	}
}
