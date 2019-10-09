package controllers;

import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import repositorios.RepositorioEventos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerEventos {
	public static ModelAndView listar(Request req, Response res) {
		// TODO: Obtener el id del user de las cookies o de otro lado
		List<Evento> listaDeEventos = new RepositorioEventos().obtenerEventosDeUsuario(1L);

		// Saco los eventos repetitivos
		List<Evento> listaDeEventosNoRepetitivos = listaDeEventos
				.stream()
				.filter(e -> e.getFrecuencia() == FrecuenciaEvento.UNICA_VEZ)
				.collect(Collectors.toList());

		return new ModelAndView(listaDeEventosNoRepetitivos, "eventos/index.hbs");
	}
}
