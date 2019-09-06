package cron_jobs;

import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.usuario.Usuario;
import repositorios.RepositorioUsuarios;

import java.util.List;
import java.util.TimerTask;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EscucharAlertasMeteorologicas extends TimerTask {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		EntityManager manager = emf.createEntityManager();
		
		List<AlertaMeteorologica> alertas = ServicioDelClima.getInstance().obtenerAlertasMeteorologicas();
		List<Usuario> usuarios = (List<Usuario>)manager.createQuery("FROM Usuario").getResultList();
				//RepositorioUsuarios.getInstance().getUsuarios(); Esto habria que sacarlo y usar hibernate para obtener a todos los usuarios
		
		alertas.forEach(alerta -> usuarios.forEach(user -> user.recibirAlertaMeteorologica(alerta)));
		
		manager.close();
	}
}
