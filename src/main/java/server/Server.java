package server;

import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Server {
	public static void main(String[] args) {
		Spark.port(9001);
		Spark.init();

		ControllerGuardarropas controller = new ControllerGuardarropas();

		Spark.get("/guardarropa/prendas",
				controller::prendas,
				new HandlebarsTemplateEngine());

		DebugScreen.enableDebugScreen();
	}

}
