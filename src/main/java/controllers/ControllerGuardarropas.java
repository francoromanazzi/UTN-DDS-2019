package controllers;

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
	public ModelAndView listar(Request req, Response res) {
		List<Guardarropa> listaDeGuardarropas = new RepositorioGuardarropas().obtenerTodos();
		return new ModelAndView(listaDeGuardarropas, "guardarropas/index.hbs");
	}

	public ModelAndView listarPrendas(Request req, Response res) {
		long id = Long.parseLong(req.params("id"));
		Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(id);
		return new ModelAndView(guardarropa, "guardarropas/prendas/index.hbs");
	}
}
