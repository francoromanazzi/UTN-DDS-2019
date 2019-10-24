package controllers;

import excepciones.CapacidadExcedidaGuardarropaException;
import excepciones.MaterialNoTieneSentidoParaEseTipoException;
import excepciones.UsuarioNoEsPropietarioDelGuardarropaException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioGuardarropas;
import repositorios.RepositorioPrendas;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Token;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControllerPrendas implements WithGlobalEntityManager, TransactionalOps {

	public static ModelAndView listar(Request req, Response res) {
		long idGuardarropa = Long.parseLong(req.params("id"));
		Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(idGuardarropa);
		return new ModelAndView(guardarropa, "guardarropas/prendas/index.hbs");
	}

	public static ModelAndView crear(Request req, Response res) {
		Map<String, Object> model = new HashMap<>();
		long idGuardarropa = Long.parseLong(req.params("id"));

		Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(idGuardarropa);

		model.put("guardarropa", guardarropa);
		model.put("tiposDePrendaPosibles", EnumSet.allOf(Tipo.class));
		model.put("materialesDePrendaPosibles", EnumSet.allOf(Material.class));

		return new ModelAndView(model, "guardarropas/prendas/nuevaPrenda.hbs");
	}

	public static String add(Request req, Response res) throws MaterialNoTieneSentidoParaEseTipoException, UsuarioNoEsPropietarioDelGuardarropaException, CapacidadExcedidaGuardarropaException {
		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));
		Usuario usuario = new RepositorioUsuarios().buscarPorId(idUser);

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

		Prenda prenda = new Prenda(tipo, material, colorPrincipal, colorSecundario, Optional.empty()); // TODO: La imagen por ahora no se puede cargar.

		new RepositorioPrendas().guardar(prenda, guardarropa, usuario);

		res.status(200);
		res.redirect("/guardarropas");

		return null;
	}

	public static void materialNoTieneSentido(MaterialNoTieneSentidoParaEseTipoException ex, Request req, Response res) {
		res.redirect("/error");
	}

	public static void usuarioNoEsPropietarioDelGuardarropa(UsuarioNoEsPropietarioDelGuardarropaException ex, Request req, Response res) {
		res.redirect("/error");
	}

	public static void capacidadExcedidaGuardarropa(CapacidadExcedidaGuardarropaException ex, Request req, Response res) {
		res.redirect("/error");
	}
}
