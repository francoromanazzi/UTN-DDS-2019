package server;

import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.*;

public class Server {
	public static void main(String[] args) {
		Spark.port(9000);
		Spark.staticFiles.location("/public");
		Spark.init();

		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.withHelper("base64", Base64Helper.isTrue)
				.withHelper("sinGuionesBajos", SinGuionesBajosHelper.isTrue)
				.withHelper("sumar1", Sumar1Helper.isTrue)
				.withHelper("size", SizeHelper.isTrue)
				.build();

		Router.configurar(engine);

		DebugScreen.enableDebugScreen();
	}
}