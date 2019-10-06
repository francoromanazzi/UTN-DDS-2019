package server;

import hardcodear_datos_db.HardcodearDatosDB;
import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
		HardcodearDatosDB.save();
		Spark.port(9001);
		Spark.staticFiles.location("/public");
		Spark.init();

		Router.configurar();

		DebugScreen.enableDebugScreen();
	}
}