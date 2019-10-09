package server;

import controllers.ControllerGuardarropas;
import controllers.ControllerLogin;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Router {
	public static void configurar(HandlebarsTemplateEngine engine) {
		Spark.get("/", ControllerLogin::show, engine);
		Spark.post("/login", ControllerLogin::login);
		Spark.post("/logout", ControllerLogin::logout);
		Spark.get("/loginFailed", ControllerLogin::loginFailed, engine);
		
		Spark.get("/guardarropas", ControllerGuardarropas::listar, engine);
		Spark.get("/guardarropas/:id/prendas", ControllerGuardarropas::listarPrendas, engine);
	}
}
