package controllers;

import excepciones.SugerenciaNoEncontradaException;
import modelo.evento.Evento;
import modelo.sugerencia.CalificacionSugerencia;
import modelo.sugerencia.SensibilidadTemperatura;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Usuario;
import repositorios.RepositorioEventos;
import repositorios.RepositorioSugerencias;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Token;

import java.util.ArrayList;
import java.util.List;

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

		new RepositorioSugerencias().aceptar(sugerencia, usuario);

		res.redirect("/eventos?sugerenciaAceptada=1");

		return null;
	}

	public static void noEncontrado(SugerenciaNoEncontradaException ex, Request req, Response res) {
		res.redirect("/error");
	}

	public static ModelAndView listarAceptadasParaCalificarlas(Request req, Response res) {
		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));
		List<Sugerencia> sugerenciasAceptadasDelUser = new RepositorioSugerencias().obtenerTodasLasAceptadasDelUsuario(idUser);
		return new ModelAndView(sugerenciasAceptadasDelUser, "sugerenciasAceptadas/index.hbs");
	}

	public static String calificar(Request req, Response res) throws SugerenciaNoEncontradaException {
		long idSugerencia = Long.parseLong(req.queryParams("id_sugerencia"));
		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));
		String nivel_abrigo_global = req.queryParams("nivel_abrigo_global");

		Usuario usuario = new RepositorioUsuarios().buscarPorId(idUser);
		Sugerencia sugerencia = new RepositorioSugerencias().buscarPorId(idSugerencia);

		SensibilidadTemperatura sensibilidadGlobal = SensibilidadTemperatura.fromString(nivel_abrigo_global);
		CalificacionSugerencia calificacionSugerencia = new CalificacionSugerencia(sensibilidadGlobal, new ArrayList<>());

		new RepositorioSugerencias().calificar(sugerencia, usuario, calificacionSugerencia);

		res.redirect("/eventos?sugerenciaCalificada=1");

		return null;
	}
}
