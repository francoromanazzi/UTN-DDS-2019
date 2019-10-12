package controllers;

import excepciones.GuardarropaNoEncontradoException;
import modelo.guardarropa.Guardarropa;
import repositorios.RepositorioGuardarropas;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Token;

import java.util.List;

public class ControllerGuardarropas {

	public static boolean isAuth(String cookieIdUser) {
		return cookieIdUser != null;
	}
	
	public static ModelAndView listar(Request req, Response res) {
		try {
			String idUser = Token.Desencriptar( req.cookie("userId") );
			if(isAuth(idUser)) {
				List<Guardarropa> listaDeGuardarropas = new RepositorioGuardarropas().obtenerTodosDelUsuario(Long.parseLong(idUser));
				return new ModelAndView(listaDeGuardarropas, "guardarropas/index.hbs");
			}
			else {
				res.redirect("/");
				return null;
			}
		}
		catch(Exception e) {
			res.redirect("/404?msg=Error al listar guardarropas");
			return null;
		}
	}

	public static ModelAndView listarPrendas(Request req, Response res) throws GuardarropaNoEncontradoException {
		try {
			String idUser = Token.Desencriptar( req.cookie("userId") );
			if(isAuth(idUser)) {
				long id = Long.parseLong(req.params("id"));
				Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(id);

				if(guardarropa.tieneUsuario(new RepositorioUsuarios().getUsuarioById(Long.parseLong(idUser))))
					return new ModelAndView(guardarropa, "guardarropas/prendas/index.hbs");
				else {
					res.redirect("/404?msg=Error al listar prendas");
					return null;
				}
			}
			else {
				res.redirect("/");
				return null;
			}
		}
		catch(Exception e) {
			res.redirect("/404?msg=Error al listar prendas");
			return null;
		}
	}

	public static void noEncontrado(GuardarropaNoEncontradoException ex, Request req, Response res) {
		res.redirect("/404?msg=Guardarropa no encontrado");
	}
}
