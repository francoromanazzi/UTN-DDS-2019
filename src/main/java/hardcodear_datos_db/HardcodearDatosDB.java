package hardcodear_datos_db;

import modelo.guardarropa.Guardarropa;
import modelo.prenda.*;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.io.File;
import java.util.Optional;

public class HardcodearDatosDB implements WithGlobalEntityManager, TransactionalOps {

	public static void main(String[] args) {
		new HardcodearDatosDB().hardcodear();
	}

	private void hardcodear() {
		this.beginTransaction();

		Usuario user1 = new Usuario("Juan Perez", "utnquemepongo@gmail.com", "1111", "juan123", "juan123");
		entityManager().persist(user1);

		Guardarropa guardarropa1 = new Guardarropa();
		user1.addGuardarropa(guardarropa1);

		user1.addPrenda(
				new Prenda(
						Tipo.REMERA_MANGA_CORTA,
						Material.ALGODON,
						new Color(255, 109, 61),
						Optional.empty(),
						Optional.of(new Imagen(new File("src/main/java/hardcodear_datos_db/Remera.jpg")))),
				guardarropa1
		);

		user1.addPrenda(
				new Prenda(
						Tipo.CAMPERA,
						Material.POLIESTER,
						new Color(0, 0, 0),
						Optional.empty(),
						Optional.of(new Imagen(new File("src/main/java/hardcodear_datos_db/Campera.jpg")))),
				guardarropa1
		);

		user1.addPrenda(
				new Prenda(
						Tipo.PANTALON_LARGO,
						Material.DENIM,
						new Color(40, 50, 255),
						Optional.empty(),
						Optional.of(new Imagen(new File("src/main/java/hardcodear_datos_db/Jean.jpg")))),
				guardarropa1
		);

		user1.addPrenda(
				new Prenda(
						Tipo.PANTALON_CORTO,
						Material.ALGODON,
						new Color(255, 50, 50),
						Optional.empty(),
						Optional.of(new Imagen(new File("src/main/java/hardcodear_datos_db/PantalonCorto.jpg")))),
				guardarropa1
		);

		user1.addPrenda(
				new Prenda(
						Tipo.ZAPATILLAS,
						Material.CUERO,
						new Color(255, 255, 255),
						Optional.empty(),
						Optional.of(new Imagen(new File("src/main/java/hardcodear_datos_db/Zapatillas.jpg")))),
				guardarropa1
		);

		entityManager().persist(guardarropa1);

		Guardarropa guardarropa2 = new Guardarropa();
		user1.addGuardarropa(guardarropa2);

		user1.addPrenda(
				new Prenda(
						Tipo.GORRO,
						Material.LANA,
						new Color(150, 109, 150),
						Optional.empty(),
						Optional.of(new Imagen(new File("src/main/java/hardcodear_datos_db/Gorro.jpg")))),
				guardarropa2
		);

		user1.addPrenda(
				new Prenda(
						Tipo.CAMISA,
						Material.POLIESTER,
						new Color(0, 0, 0),
						Optional.empty(),
						Optional.of(new Imagen(new File("src/main/java/hardcodear_datos_db/Camisa.jpg")))),
				guardarropa2
		);

		user1.addPrenda(
				new Prenda(
						Tipo.PANTALON_CORTO,
						Material.DENIM,
						new Color(0, 0, 255),
						Optional.of(new Color(255, 255, 255)),
						Optional.empty()),
				guardarropa2
		);

		user1.addPrenda(
				new Prenda(
						Tipo.ZAPATILLAS,
						Material.CUERO,
						new Color(0, 0, 0),
						Optional.of(new Color(255, 255, 255)),
						Optional.empty()),
				guardarropa2
		);

		entityManager().persist(guardarropa2);

		this.commitTransaction();
	}
}
