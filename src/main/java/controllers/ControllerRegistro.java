package controllers;

import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioGuardarropas;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerRegistro implements WithGlobalEntityManager, TransactionalOps {
    public static ModelAndView mostrar(Request req, Response res){
        return new ModelAndView(null, "registro/index.hbs");
    }

    public String registrar(Request req, Response res){
        String nombre = req.queryParams("nombre");
        String username = req.queryParams("username");
        String password = req.queryParams("password");
        String mail = req.queryParams("mail");
        String numero = req.queryParams("numero");

        Usuario user = new Usuario(nombre, mail, numero, username, password);

        withTransaction(() -> {
            new RepositorioUsuarios().nuevoUsuario(user);
        });

        res.status(200);
        res.redirect("/");

        return null;
    }

}
