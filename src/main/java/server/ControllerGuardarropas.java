package server;

import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Optional;

public class ControllerGuardarropas {

	public ModelAndView prendas(Request req, Response res) {
		Guardarropa guardarropas = new Guardarropa();
		guardarropas.addPrenda(new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.empty()));

		return new ModelAndView(guardarropas, "guardarropas.hbs");
	}

}
