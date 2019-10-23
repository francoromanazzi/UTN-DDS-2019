package server;

import controllers.*;
import excepciones.*;
import spark.ModelAndView;
import spark.Spark;
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

		ControllerRegistro controllerRegistro = new ControllerRegistro();
		get("/registro", ControllerRegistro::mostrar, engine);
		post("/registrarse", controllerRegistro::registrar);

		ControllerPrendas controllerPrendas = new ControllerPrendas();
		before("/guardarropas", Auth::tieneToken);
		before("/guardarropas/*", Auth::tieneToken);
		get("/guardarropas", ControllerGuardarropas::listar, engine);
		before("/guardarropas/:id/prendas", Auth::userEsPropietarioDeGuardarropa);
		get("/guardarropas/:id/prendas", ControllerPrendas::listar, engine);
		get("/guardarropas/:id/nuevaPrenda", ControllerPrendas::crear,engine);
		before("/guardarropas/:id/nuevaPrenda", Auth::userEsPropietarioDeGuardarropa);
		post("/guardarropas/:id/nuevaPrenda", controllerPrendas::add);
		exception(MaterialNoTieneSentidoParaEseTipoException.class, ControllerPrendas::materialNoTieneSentido);
		exception(GuardarropaNoEncontradoException.class, ControllerGuardarropas::noEncontrado);

		before("/eventos", Auth::tieneToken);
		before("/eventos/*", Auth::tieneToken);
		get("/eventos", ControllerEventos::listar, engine);
		before("/eventos/:id/sugerencias", Auth::userEsPropietarioDeEvento);
		get("/eventos/:id/sugerencias", ControllerSugerencias::listar, engine);
		exception(EventoNoEncontradoException.class, ControllerEventos::noEncontrado);
		before("/eventos/:id/sugerencias/aceptadas", Auth::userEsPropietarioDeEvento);
		post("/eventos/:id/sugerencias/aceptadas", ControllerSugerencias::aceptar);
		exception(SugerenciaNoEncontradaException.class, ControllerSugerencias::noEncontrado);

		before("/sugerencias/aceptadas", Auth::tieneToken);
		get("/sugerencias/aceptadas", ControllerSugerencias::listarAceptadasParaCalificarlas, engine);
		before("/sugerencias/calificadas", Auth::tieneToken);
		post("/sugerencias/calificadas", ControllerSugerencias::calificar);
	}
}
