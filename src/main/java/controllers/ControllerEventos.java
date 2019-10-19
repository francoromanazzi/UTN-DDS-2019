package controllers;

import excepciones.EventoNoEncontradoException;
import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import repositorios.RepositorioEventos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Token;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerEventos {

	public static ModelAndView listar(Request req, Response res) {
		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));
		List<Evento> listaDeEventos = new RepositorioEventos().obtenerEventosDeUsuario(idUser);

		// Saco los eventos repetitivos
		List<Evento> listaDeEventosNoRepetitivos = listaDeEventos
				.stream()
				.filter(e -> e.getFrecuencia() == FrecuenciaEvento.UNICA_VEZ)
				.collect(Collectors.toList());

		return new ModelAndView(listaDeEventosNoRepetitivos, "eventos/index.hbs");
	}

	public static ModelAndView listarSugerencias(Request req, Response res) {
		long idEvento = Long.parseLong(req.params("id"));
		Evento evento = new RepositorioEventos().buscarPorId(idEvento);
		return new ModelAndView(evento, "eventos/sugerencias/index.hbs");
	}

	public static void noEncontrado(EventoNoEncontradoException ex, Request req, Response res) {
		res.redirect("/error");
	}

	public static String aceptarSugerencia(Request req, Response res) {
		long idEvento = Long.parseLong(req.params("id"));
		long idSugerencia = Long.parseLong(req.queryParams("id_sugerencia"));

		System.out.println(idEvento + ", " + idSugerencia);

		res.redirect("/eventos");

		return null;
	}
}
