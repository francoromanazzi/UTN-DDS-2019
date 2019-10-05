package server;

import modelo.guardarropa.Guardarropa;
import modelo.prenda.*;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.List;
import java.util.Optional;

public class ControllerGuardarropas implements WithGlobalEntityManager {

	public ModelAndView prendas(Request req, Response res) {
		Guardarropa guardarropas = new Guardarropa();
		guardarropas.addPrenda(new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.empty()));

		return new ModelAndView(guardarropas, "guardarropas.hbs");
	}

	public ModelAndView listarPrendas(Request req, Response res){
		Long id = Long.parseLong(req.params("id")); // obtenemos el valor del parametro de ruta: "id" que definimos en la ruta (devuelve un string)

		// Obetener guardarropa con el id obtenido
		// Guardarropa guardarropas = RepoDeGuardarropas.findById(id);
		Guardarropa guardarropas = new Guardarropa();
		guardarropas.addPrenda(new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.empty()));

		return new ModelAndView(guardarropas, "guardarropas.hbs");
	}

	public ModelAndView guardarropas(Request req, Response res){

		List<Guardarropa> listaDeGuardarropas = entityManager().createQuery("from Guardarropa").getResultList();

		return new ModelAndView(listaDeGuardarropas, "listadoDeGuardarropas.hbs");
	}

}
