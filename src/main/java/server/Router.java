package server;

import controllers.ControllerEventos;
import controllers.ControllerGuardarropas;
import controllers.ControllerLogin;
import excepciones.EventoNoEncontradoException;
import excepciones.GuardarropaNoEncontradoException;
import excepciones.UsuarioNoEncontradoException;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import utils.Auth;

import static spark.Spark.*;

public class Router {
	public static void configurar(HandlebarsTemplateEngine engine) {
		notFound(((req, res) -> {
			res.redirect("/error");
			return null;
		}));

		get("/error", (req, res) -> new ModelAndView(null, "error.hbs"), engine);

		get("/", ControllerLogin::mostrar, engine);
		post("/login", ControllerLogin::login);
		exception(UsuarioNoEncontradoException.class, ControllerLogin::userNoEncontrado);
		get("/logout", ControllerLogin::logout);
		get("/loginFailed", ControllerLogin::loginFailed, engine);

		before("/guardarropas", Auth::tieneToken);
		before("/guardarropas/*", Auth::tieneToken);
		get("/guardarropas", ControllerGuardarropas::listar, engine);
		before("/guardarropas/:id/prendas", Auth::userEsPropietarioDeGuardarropa);
		get("/guardarropas/:id/prendas", ControllerGuardarropas::listarPrendas, engine);
		exception(GuardarropaNoEncontradoException.class, ControllerGuardarropas::noEncontrado);

		before("/eventos", Auth::tieneToken);
		before("/eventos/*", Auth::tieneToken);
		get("/eventos", ControllerEventos::listar, engine);
		before("/eventos/:id/sugerencias", Auth::userEsPropietarioDeEvento);
		get("/eventos/:id/sugerencias", ControllerEventos::listarSugerencias, engine);
		exception(EventoNoEncontradoException.class, ControllerEventos::noEncontrado);
		post("/eventos/:id/sugerencias/aceptadas", ControllerEventos::aceptarSugerencia);
	}
}
