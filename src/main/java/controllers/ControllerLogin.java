package controllers;

import excepciones.DesencriptacionException;
import excepciones.UsuarioNoEncontradoException;
import modelo.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.SHA256Builder;
import utils.Token;

public class ControllerLogin {

	public static ModelAndView mostrar(Request req, Response res) {
		try {
			String cookie = Token.Desencriptar(req.cookie("userId"));
			if(!cookie.isEmpty())
				res.redirect("/guardarropas");
		}catch(DesencriptacionException e) {}
		
		return new ModelAndView(null, "login/index.hbs");
	}

	public static ModelAndView loginFailed(Request req, Response res) {
		return new ModelAndView(null, "login/index.hbs");
	}

	public static String login(Request req, Response res) throws UsuarioNoEncontradoException {
		String username = req.queryParams("username");
		String pass = req.queryParams("password");

		Usuario user = new RepositorioUsuarios().buscarPorCredenciales(username, pass);

		res.status(200);
		res.cookie("userId", Token.Encriptar(user.getId().toString()));
		res.redirect("/guardarropas");

		return null;
	}

	public static void userNoEncontrado(UsuarioNoEncontradoException ex, Request req, Response res) {
		res.redirect("/?loginFailed=1");
	}

	public static String logout(Request req, Response res) {
		res.status(200);
		res.removeCookie("userId");

		res.redirect("/?logoutExitoso=1");
		return null;
	}
}
