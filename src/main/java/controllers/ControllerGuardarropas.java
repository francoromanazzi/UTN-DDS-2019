package controllers;

import excepciones.GuardarropaNoEncontradoException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import repositorios.RepositorioGuardarropas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Optional;

public class ControllerGuardarropas {
	//Chequear si usuario esta autenticado en cada request
	
	public static ModelAndView listar(Request req, Response res) {
		List<Guardarropa> listaDeGuardarropas = new RepositorioGuardarropas().obtenerTodos();
		return new ModelAndView(listaDeGuardarropas, "guardarropas/index.hbs");
	}

	public static ModelAndView listarPrendas(Request req, Response res) throws GuardarropaNoEncontradoException {
		long id = Long.parseLong(req.params("id"));
		Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(id);
		return new ModelAndView(guardarropa, "guardarropas/prendas/index.hbs");
	}

	public static void noEncontrado(GuardarropaNoEncontradoException ex, Request req, Response res) {
		res.redirect("/404?msg=Guardarropa no encontrado");
	}
}
