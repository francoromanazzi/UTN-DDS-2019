package server;

import controllers.ControllerEventos;
import controllers.ControllerGuardarropas;
import controllers.ControllerLogin;
import excepciones.GuardarropaNoEncontradoException;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;

public class Router {
	public static void configurar(HandlebarsTemplateEngine engine) {
		notFound(((request, response) -> {
			response.redirect("/404");
			return null;
		}));
		
		get("/404", (req, res) -> {
			String msg = req.queryParams("msg") != null ? req.queryParams("msg") : "Recurso no encontrado";
			return new ModelAndView(msg, "404.hbs");
		}, engine);

		get("/login", ControllerLogin::mostrar, engine);
		post("/login", ControllerLogin::login);
		get("/logout", ControllerLogin::logout);
		get("/loginFailed", ControllerLogin::loginFailed, engine);

		get("/guardarropas", ControllerGuardarropas::listar, engine);
		get("/guardarropas/:id/prendas", ControllerGuardarropas::listarPrendas, engine);
		exception(GuardarropaNoEncontradoException.class, ControllerGuardarropas::noEncontrado);

		get("/eventos", ControllerEventos::listar, engine);
		
		
	}
}
