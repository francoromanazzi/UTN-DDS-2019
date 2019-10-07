package controllers;

import modelo.usuario.Usuario;
import servicios.UsuarioService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.SHA256Builder;

public class LoginController {
	
    public static ModelAndView show(Request req, Response res) {
        return new ModelAndView(null, "login/index.hbs");
    }
    
    public static ModelAndView loginFailed(Request req, Response res) {
        return new ModelAndView(null, "login/loginFailed.hbs");
    }
    
    public static String login(Request req, Response res) {
    	String username = req.queryParams("username");
        String pass = req.queryParams("password");
        String password = SHA256Builder.generarHash(pass);
        Usuario user = new UsuarioService().getUsuarioByCredentials(username, pass);

        try {
            if (username.isEmpty() || pass.isEmpty() || user == null) {
            	res.redirect("/loginFailed");
            }
               
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
            	res.status(200);
            	res.cookie("userId", user.getId().toString());
                req.session().attribute("username", username);
                req.session().attribute("password", password);
                res.redirect("/guardarropas");
            }
            
        } catch (Exception e) {
        	res.status(500);
        	res.body(e.toString());
        }
        
        return null;
    }
    
    public static String logout(Request req, Response res) {
    	res.status(200);
    	res.removeCookie("userId");
        req.session().removeAttribute("username");
        req.session().removeAttribute("password");

        res.redirect("/");
        return null;
    }
}
