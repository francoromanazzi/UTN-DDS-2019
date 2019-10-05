package server;

import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Server {
	public static void main(String[] args) {
		Spark.port(9001);
		Spark.init();

		ControllerGuardarropas controller = new ControllerGuardarropas();

		/*
		Tipos de parametros:
			* path param (parametro de ruta), se escribe como ":param" (se usa con todos los metodos)
			* query param, se escribe como "?param=valor" (se usa con todos los metodos, mas comunmente con GET)
			* body param, es lo que va adentro de un POST/PUT/PATCH
		 */
		Spark.get("/guardarropas",
				controller::guardarropas,
				new HandlebarsTemplateEngine());

		DebugScreen.enableDebugScreen();
	}

}