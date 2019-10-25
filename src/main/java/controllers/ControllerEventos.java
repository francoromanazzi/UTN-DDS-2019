package controllers;

import excepciones.EventoNoEncontradoException;
import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import modelo.evento.TipoEvento;
import modelo.guardarropa.Guardarropa;
import modelo.usuario.Usuario;
import repositorios.RepositorioEventos;
import repositorios.RepositorioGuardarropas;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Token;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public static void noEncontrado(EventoNoEncontradoException ex, Request req, Response res) {
		res.redirect("/error");
	}
	
	public static ModelAndView nuevoEvento(Request req, Response res) {
		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));
		List<Guardarropa> listaDeGuardarropas = new RepositorioGuardarropas().obtenerTodosDelUsuario(idUser);
		
		return new ModelAndView(listaDeGuardarropas, "eventos/nuevo.hbs");
	}
	
	public static String registrarEventoNuevo(Request req, Response res) {
		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));
		Usuario user = new RepositorioUsuarios().buscarPorId(idUser);
		
		LocalDateTime fechaInicio = LocalDateTime.parse(req.queryParams("fecha_inicio"));
		LocalDateTime fechaFin = LocalDateTime.parse(req.queryParams("fecha_fin"));
		FrecuenciaEvento frecuencia = FrecuenciaEvento.valueOf(req.queryParams("frecuencia").toUpperCase().replace(" ", "_"));
		TipoEvento tipo = TipoEvento.valueOf(req.queryParams("tipo").toUpperCase());
		Guardarropa g = new RepositorioGuardarropas().buscarPorId(Long.parseLong(req.queryParams("id_guardarropa"))); 
		
		Evento nuevoEvento = new Evento(req.queryParams("titulo"), fechaInicio, fechaFin, frecuencia, tipo);

		user.agendarEvento(nuevoEvento, g);
		new RepositorioEventos().guardar(nuevoEvento);

		res.redirect("/eventos?eventCreated=1");
		return null;
	}
}
