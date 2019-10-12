package hardcodear_datos_db;

import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import modelo.evento.TipoEvento;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.*;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioGuardarropas;
import repositorios.RepositorioUsuarios;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

public class HardcodearDatosDB implements WithGlobalEntityManager, TransactionalOps {

	public static void main(String[] args) {
		HardcodearDatosDB hardcoder = new HardcodearDatosDB();
		hardcoder.hardcodear();
	}

	private void hardcodear() {
		this.beginTransaction();

		Usuario user1 = new Usuario("Juan Perez", "utnquemepongo@gmail.com", "1111", "juan123", "juan123");
		Usuario user2 = new Usuario("Carlos Gonzalez", "utnquemepongo@gmail.com", "1111", "carlos123", "carlos123");
		entityManager().persist(user1);
		entityManager().persist(user2);

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
						Tipo.REMERA_MANGA_LARGA,
						Material.ALGODON,
						new Color(255, 109, 61),
						Optional.empty(),
						Optional.empty()),
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
		user2.addGuardarropa(guardarropa2); // Guardarropa compartido

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
						Tipo.REMERA_MANGA_CORTA,
						Material.ALGODON,
						new Color(0, 0, 0),
						Optional.empty(),
						Optional.empty()),
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

	public void agendarEventos() {
		this.beginTransaction();

		Usuario user1 = new RepositorioUsuarios().getUsuarioById(1L);
		Usuario user2 = new RepositorioUsuarios().getUsuarioById(2L);

		Guardarropa guardarropa1 = new RepositorioGuardarropas().buscarPorId(1L);
		Guardarropa guardarropa2 = new RepositorioGuardarropas().buscarPorId(2L);

		Evento ev1 = new Evento("Ir a la facultad", LocalDateTime.now().plusSeconds(30), LocalDateTime.now().plusHours(4), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
		Evento ev2 = new Evento("Ir al trabajo", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(8), FrecuenciaEvento.UNICA_VEZ, TipoEvento.FORMAL);
		Evento ev3 = new Evento("Ir a correr", LocalDateTime.now().plusSeconds(30), LocalDateTime.now().plusHours(4), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);

		entityManager().persist(ev1);
		entityManager().persist(ev2);
		entityManager().persist(ev3);

		this.commitTransaction();

		this.beginTransaction();

		user1.agendarEvento(ev1, guardarropa1);
		user1.agendarEvento(ev2, guardarropa1);
		user2.agendarEvento(ev3, guardarropa2);

		this.commitTransaction();
	}

}
