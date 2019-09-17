package UTN.QueMePongo.Persistencia;

import modelo.prenda.*;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TestPersistenciaImagen extends AbstractPersistenceTest implements WithGlobalEntityManager {
	private final String
			UrlArchivoGrande = "src/test/resources/img/RemeraArchivoGrande.jpg",
			UrlArchivoChico = "src/test/resources/img/RemeraArchivoChico.jpg",
			UrlArchivoGrandeDesdeLaDB = "src/test/resources/img/RemeraArchivoGrandeDesdeLaDB.jpg",
			UrlArchivoChicoDesdeLaDB = "src/test/resources/img/RemeraArchivoChicoDesdeLaDB.jpg";

	@Test
	public void persistirPrendaConImagenYLaObtengoReescalada() throws IOException {
		Imagen imagenReescalada = new Imagen(new File(UrlArchivoGrande));
		Prenda remera = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(200, 50, 30), Optional.empty(), Optional.of(imagenReescalada));

		entityManager().persist(remera);

		Imagen imagenDB = entityManager().createQuery("FROM Imagen WHERE Id = :id", Imagen.class)
				.setParameter("id", imagenReescalada.getId())
				.getSingleResult();

		imagenDB.escribirEnArchivo(UrlArchivoGrandeDesdeLaDB);

		assertEquals(imagenReescalada.getImagen().getWidth(), imagenDB.getImagen().getWidth());
		assertEquals(imagenReescalada.getImagen().getWidth(), imagenDB.getImagen().getHeight());
	}

	@Test
	public void persistirPrendaConImagenVaciaYSePoneNULL() {
		Prenda remera = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(200, 50, 30), Optional.empty(), Optional.empty());

		entityManager().persist(remera);

		Prenda prendaDB = entityManager().createQuery("FROM Prenda", Prenda.class).getSingleResult();

		assertEquals(Optional.empty(), prendaDB.getImagen());

		List<Imagen> imagenesDB = entityManager().createQuery("FROM Imagen", Imagen.class).getResultList();

		assertEquals(0, imagenesDB.size());
	}
}
