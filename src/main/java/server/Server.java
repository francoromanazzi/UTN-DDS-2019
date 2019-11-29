package server;

import hardcodear_datos_db.HardcodearDatosDB;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.pronosticos_del_clima.proveedores.AccuWeather;
import modelo.pronosticos_del_clima.proveedores.DarkSky;
import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.*;

public class Server {
	public static void main(String[] args) {
		Spark.port(getPort());

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

		//DebugScreen.enableDebugScreen();

		ServicioDelClima.getInstance().agregarMeteorologo(new AccuWeather());
		ServicioDelClima.getInstance().agregarMeteorologo(new DarkSky());
		//new HardcodearDatosDB().agendarEventos();
	}

	private static int getPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();

		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}

		return 9000;
	}
}