package controllers;

import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
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

        if(entityManager().createQuery("From Usuarios Where username="+username, Usuario.class).getResultList().size() != 0) {
            //ERROR, El nombre de usuario ya está en uso;
        }

        Usuario user = new Usuario(nombre, mail, numero, username, password);

        withTransaction(() -> {
            new RepositorioUsuarios().nuevoUsuario(user);
        });

        res.status(200);
        res.redirect("/");

        return null;
    }

}
