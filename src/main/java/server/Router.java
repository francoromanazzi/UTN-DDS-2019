package server;

import controllers.ControllerGuardarropas;
import controllers.LoginController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;

public class Router {
	public static void configurar(HandlebarsTemplateEngine engine) {
		ControllerGuardarropas controllerGuardarropas = new ControllerGuardarropas();

		path("/guardarropas", () -> {
			path("", () -> {
				get("", controllerGuardarropas::listar, engine);
			});
			path("/:id/prendas", () -> {
				get("", controllerGuardarropas::listarPrendas, engine);
			});
		});
		Spark.get("/login", LoginController::show, engine);
	}
}
