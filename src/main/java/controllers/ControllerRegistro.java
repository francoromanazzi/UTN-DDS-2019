package controllers;

import excepciones.UsernameEnUsoException;
import modelo.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerRegistro {
	public static ModelAndView mostrar(Request req, Response res) {
		return new ModelAndView(null, "registro/index.hbs");
	}

	public static String registrar(Request req, Response res) throws UsernameEnUsoException {
		String nombre = req.queryParams("nombre");
		String username = req.queryParams("username");
		String password = req.queryParams("password");
		String mail = req.queryParams("mail");
		String numero = req.queryParams("numero");

        Usuario user = new Usuario(nombre, mail, numero, username, password);
		new RepositorioUsuarios().guardar(user);

		res.redirect("/?registroExitoso=1");

		return null;
	}

	public static void usernameEnUso(UsernameEnUsoException ex, Request req, Response res) {
		res.redirect("/registro?usernameEnUso=1");
	}

}
