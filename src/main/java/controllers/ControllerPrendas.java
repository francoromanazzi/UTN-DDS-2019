package controllers;

import excepciones.*;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.*;
import modelo.usuario.Usuario;
import org.apache.commons.io.IOUtils;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioGuardarropas;
import repositorios.RepositorioPrendas;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Token;

import javax.imageio.ImageIO;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

	public static String add(Request req, Response res) throws MaterialNoTieneSentidoParaEseTipoException, UsuarioNoEsPropietarioDelGuardarropaException, CapacidadExcedidaGuardarropaException, ImagenNoPudoSerLeidaException {
		//req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

		if (req.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
			MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
			req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
		}


		long idUser = Long.parseLong(Token.Desencriptar(req.cookie("userId")));
		Usuario usuario = new RepositorioUsuarios().buscarPorId(idUser);

		long idGuardarropa = Long.parseLong(req.params("id"));
		Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(idGuardarropa);

		String valueTipo = null;
		try {
			Part partTipo = req.raw().getPart("tipo");
			valueTipo = IOUtils.toString(partTipo.getInputStream(), StandardCharsets.UTF_8);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
		Tipo tipo = Tipo.valueOf(valueTipo);

		String valueMaterial = null;
		try {
			Part partMaterial = req.raw().getPart("material");
			valueMaterial = IOUtils.toString(partMaterial.getInputStream(), StandardCharsets.UTF_8);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
		Material material = Material.valueOf(valueMaterial);

		String valueColor = null;
		try {
			Part partColor = req.raw().getPart("colorPrincipal");
			valueColor = IOUtils.toString(partColor.getInputStream(), StandardCharsets.UTF_8);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
		Color colorPrincipal = Color.fromString(valueColor);

		String valueColorSecundario = "";
		try {
			Part partColorSec = req.raw().getPart("colorSecundario");
			valueColorSecundario = IOUtils.toString(partColorSec.getInputStream(), StandardCharsets.UTF_8);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
		Optional<Color> colorSecundario = valueColorSecundario.equals("") ? Optional.empty() : Optional.of(Color.fromString(valueColorSecundario));

		// Imagen subida por el usuario
		Optional<Imagen> imagen = Optional.empty();
		try {
			Part filePart = req.raw().getPart("cargaFoto");

			if(filePart != null && filePart.getSize() > 0) {
				InputStream inputStream = filePart.getInputStream();
				BufferedImage bufferedImage = ImageIO.read(inputStream);
				imagen = Optional.of(new Imagen(bufferedImage));
			}

		} catch (IOException | ServletException e) {
			throw new ImagenNoPudoSerLeidaException();
		}

		Prenda prenda = new Prenda(tipo, material, colorPrincipal, colorSecundario, imagen);

		new RepositorioPrendas().guardar(prenda, guardarropa, usuario);

		res.status(200);
		res.redirect("/guardarropas?prendaAgregada=1");

		return null;
	}

	public static void materialNoTieneSentido(MaterialNoTieneSentidoParaEseTipoException ex, Request req, Response res) {
		long idGuardarropa = Long.parseLong(req.params("id"));
		res.redirect("/guardarropas/" + idGuardarropa + "/nuevaPrenda?materialNoTieneSentido=1");
	}

	public static void usuarioNoEsPropietarioDelGuardarropa(UsuarioNoEsPropietarioDelGuardarropaException ex, Request req, Response res) {
		long idGuardarropa = Long.parseLong(req.params("id"));
		res.redirect("/guardarropas/" + idGuardarropa + "/nuevaPrenda?usuarioNoEsPropietarioDelGuardarropa=1");
	}

	public static void capacidadExcedidaGuardarropa(CapacidadExcedidaGuardarropaException ex, Request req, Response res) {
		long idGuardarropa = Long.parseLong(req.params("id"));
		res.redirect("/guardarropas/" + idGuardarropa + "/nuevaPrenda?capacidadExcedidaGuardarropa=1");
	}

	public static void imagenNoPudoSerLeida(ImagenNoPudoSerLeidaException ex, Request req, Response res) {
		long idGuardarropa = Long.parseLong(req.params("id"));
		res.redirect("/guardarropas/" + idGuardarropa + "/nuevaPrenda?imagenNoPudoSerLeida=1");
	}

	public static void coloresIguales(ColoresIgualesException ex, Request req, Response res) {
		long idGuardarropa = Long.parseLong(req.params("id"));
		res.redirect("/guardarropas/" + idGuardarropa + "/nuevaPrenda?coloresIguales=1");
	}
}
