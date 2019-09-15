package UTN.QueMePongo.Persistencia;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Query;

import org.junit.Test;

import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import modelo.evento.TipoEvento;
import modelo.usuario.Usuario;
import servicios.SHA256Builder;
import servicios.Session;
import servicios.UsuarioService;

public class TestPersistencia{
	
	@Test
	public void persistirUsuarioYLoObtengo() {
		Usuario user = new Usuario("lucas","rosol@gmail.com","1554675466","luqui","asd");
	    new UsuarioService().persistir(user);
	    
	    Usuario userDB = new UsuarioService().getUsuarioByCredentials("luqui","asd");
	    assertEquals(user.getUsername(), "luqui");
	    assertEquals(user.getPassword(), SHA256Builder.generarHash("asd"));
	    
	    new UsuarioService().eliminar(userDB);
	}
	
//	@SuppressWarnings("unchecked")
	@Test
	public void persistirUsuarioConEventosYLosObtengo() {
		Usuario user = new Usuario("lucas","rosol@gmail.com","1554675466","luqui","asd");
		Evento evento1 = new Evento("Ir al supermercado",LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
		Evento evento2 = new Evento("Ir al trabajo",LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
	    user.getEventos().add(evento1);
	    user.getEventos().add(evento2);
		new UsuarioService().persistir(user);
		
	    Usuario userDB = new UsuarioService().getUsuarioByCredentials("luqui","asd");
	    assertEquals(user.getEventos().size(), 2);
	    
	    new UsuarioService().eliminar(userDB);
	    
	    List<Evento> eventos = Session.getEntityManager().createQuery("from Evento", Evento.class).getResultList();
		
		assertEquals(eventos.size(), 0);
	}
}
