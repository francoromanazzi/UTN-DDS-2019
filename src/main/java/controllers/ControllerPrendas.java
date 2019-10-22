package controllers;

import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import repositorios.RepositorioGuardarropas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.util.Optional;

public class ControllerPrendas implements WithGlobalEntityManager, TransactionalOps{

	public static ModelAndView listar(Request req, Response res) {
		long idGuardarropa = Long.parseLong(req.params("id"));
		Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(idGuardarropa);
		return new ModelAndView(guardarropa, "guardarropas/prendas/index.hbs");
	}

	public static ModelAndView crear(Request req, Response res) {
		long idGuardarropa = Long.parseLong(req.params("id"));
		Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(idGuardarropa);
		return new ModelAndView(guardarropa, "guardarropas/prendas/nuevaPrenda.hbs");
	}

	public String add(Request req, Response res) {
		long idGuardarropa = Long.parseLong(req.params("id"));
		Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(idGuardarropa);
		guardarropa.addPrenda(new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON,new Color(0, 0, 0), Optional.empty(), Optional.empty()));
		withTransaction(() ->{
			new RepositorioGuardarropas().agregarPrenda(new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON,new Color(0, 0, 0), Optional.empty(), Optional.empty()));
		});
		res.redirect("/guardarropas");
		return null;
	}


}
