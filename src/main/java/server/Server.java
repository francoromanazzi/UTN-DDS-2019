package server;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
		Spark.port(9001);
		Spark.staticFiles.location("/public");
		Spark.init();

		Router.configurar();

		DebugScreen.enableDebugScreen();
	}
}