package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerError {
    public static ModelAndView error(Request req, Response res){
        String mensajeDeError = req.params("mensaje");
        return new ModelAndView(mensajeDeError, "/error.hbs");
    }
}
