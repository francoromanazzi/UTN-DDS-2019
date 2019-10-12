package controllers;

import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import modelo.guardarropa.Guardarropa;
import repositorios.RepositorioEventos;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerEventos {
	
	public static boolean isAuth(String cookieIdUser) {
		return cookieIdUser != null;
	}
	
	public static ModelAndView listar(Request req, Response res) {
		String idUser = req.cookie("userId");
		try {
			if(isAuth(idUser)) {
				List<Evento> listaDeEventos = new RepositorioEventos().obtenerEventosDeUsuario(Long.parseLong(idUser));

				// Saco los eventos repetitivos
				List<Evento> listaDeEventosNoRepetitivos = listaDeEventos
						.stream()
						.filter(e -> e.getFrecuencia() == FrecuenciaEvento.UNICA_VEZ)
						.collect(Collectors.toList());

				return new ModelAndView(listaDeEventosNoRepetitivos, "eventos/index.hbs");
			}
			else {
				res.redirect("/");
				return null;
			}
		}
		catch(Exception e) {
			res.redirect("/404?msg=Error al listar eventos");
			return null;
		}
	}
}
