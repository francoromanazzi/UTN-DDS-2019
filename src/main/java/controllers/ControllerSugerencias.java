package controllers;

import excepciones.SugerenciaNoEncontradaException;
import modelo.evento.Evento;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Usuario;
import repositorios.RepositorioEventos;
import repositorios.RepositorioSugerencias;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Token;

public class ControllerSugerencias {

	public static ModelAndView listar(Request req, Response res) {
		long idEvento = Long.parseLong(req.params("id"));
		Evento evento = new RepositorioEventos().buscarPorId(idEvento);
		return new ModelAndView(evento, "eventos/sugerencias/index.hbs");
	}

	public static String aceptar(Request req, Response res) throws SugerenciaNoEncontradaException {
		long idSugerencia = Long.parseLong(req.queryParams("id_sugerencia"));
		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));

		Usuario usuario = new RepositorioUsuarios().buscarPorId(idUser);
		Sugerencia sugerencia = new RepositorioSugerencias().buscarPorId(idSugerencia);

		sugerencia.aceptar(usuario);

		res.redirect("/eventos");

		return null;
	}

	public static void noEncontrado(SugerenciaNoEncontradaException ex, Request req, Response res) {
		res.redirect("/error");
	}
}
