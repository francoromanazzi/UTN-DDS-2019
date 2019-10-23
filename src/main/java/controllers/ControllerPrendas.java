package controllers;

import excepciones.MaterialNoTieneSentidoParaEseTipoException;
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

		String valueTipo = req.queryParams("tipo");
		Tipo tipo = Tipo.valueOf(valueTipo);

		String valueMaterial = req.queryParams("material");
		Material material = Material.valueOf(valueMaterial);

		String valueColor = req.queryParams("colorPrincipal");
		Color colorPrincipal = Color.fromString(valueColor);

		String valueColorSecundario = req.queryParams("colorSecundario");
		Optional<Color> colorSecundario = valueColorSecundario.equals("") ? Optional.empty() : Optional.of(Color.fromString(valueColorSecundario));

		Prenda prenda = new Prenda(tipo, material, colorPrincipal, colorSecundario, Optional.empty()); // La imagen por ahora no se puede cargar.

		guardarropa.addPrenda(prenda);

		new RepositorioGuardarropas().add(guardarropa); //TODO: se persiste el guardarropas con la nueva prenda o solo la prenda con el id de guardarropas

		res.redirect("/guardarropas");

		return null;
	}

	public static void materialNoTieneSentido(MaterialNoTieneSentidoParaEseTipoException ex, Request req, Response res){
		res.redirect("/error");
	}

}
