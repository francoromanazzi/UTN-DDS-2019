package server;

import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.Base64Helper;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;
import spark.utils.SinGuionesBajosHelper;

public class Server {
	public static void main(String[] args) {
		Spark.port(9001);
		Spark.staticFiles.location("/public");
		Spark.init();

		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.withHelper("base64", Base64Helper.isTrue)
				.withHelper("sinGuionesBajos", SinGuionesBajosHelper.isTrue)
				.build();

		Router.configurar(engine);

		DebugScreen.enableDebugScreen();
	}
}