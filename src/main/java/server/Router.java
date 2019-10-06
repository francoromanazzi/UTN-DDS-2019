package server;

import controllers.ControllerGuardarropas;
import controllers.LoginController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

import static spark.Spark.*;

public class Router {
	public static void configurar() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.build();

		Spark.get("/", LoginController::show, engine);
		Spark.get("/login", LoginController::login, engine);
		Spark.get("/loginFailed", LoginController::loginFailed, engine);
		
		Spark.get("/guardarropas", ControllerGuardarropas::listar, engine);
		Spark.get("/guardarropas/:id/prendas", ControllerGuardarropas::listarPrendas, engine);
		
	}
}
