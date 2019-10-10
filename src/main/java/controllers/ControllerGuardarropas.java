package controllers;

import excepciones.GuardarropaNoEncontradoException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import repositorios.RepositorioGuardarropas;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Optional;

public class ControllerGuardarropas {

	public static boolean isAuth(String cookieIdUser) {
		return cookieIdUser != null;
	}
	
	public static ModelAndView listar(Request req, Response res) {
		String idUser = req.cookie("userId");
		try {
			if(isAuth(idUser)) {
				List<Guardarropa> listaDeGuardarropas = new RepositorioUsuarios().getGuardarropasDeUsuarioPorId(Long.parseLong(idUser));
				return new ModelAndView(listaDeGuardarropas, "guardarropas/index.hbs");
			}
			else {
				res.redirect("/");
				return null;
			}
		}
		catch(Exception e) {
			res.redirect("/404/msg=Error al listar guardarropas");
			return null;
		}
	}

	public static ModelAndView listarPrendas(Request req, Response res) throws GuardarropaNoEncontradoException {
		String idUser = req.cookie("userId");
		try {
			if(isAuth(idUser)) {
				long id = Long.parseLong(req.params("id"));
				Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(id);
				return new ModelAndView(guardarropa, "guardarropas/prendas/index.hbs");
			}
			else {
				res.redirect("/");
				return null;
			}
		}
		catch(Exception e) {
			res.redirect("/404/msg=Error al listar prendas");
			return null;
		}
	}

	public static void noEncontrado(GuardarropaNoEncontradoException ex, Request req, Response res) {
		res.redirect("/404?msg=Guardarropa no encontrado");
	}
}
