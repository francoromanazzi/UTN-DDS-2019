package controllers;

import modelo.guardarropa.Guardarropa;
import repositorios.RepositorioGuardarropas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerPrendas {

	public static ModelAndView listar(Request req, Response res) {
		long idGuardarropa = Long.parseLong(req.params("id"));
		Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(idGuardarropa);
		return new ModelAndView(guardarropa, "guardarropas/prendas/index.hbs");
	}

}
