package modelo.notificaciones;

import modelo.usuario.Usuario;

public class NotificadorSMS implements Notificador{
	private static NotificadorSMS instance = new NotificadorSMS();
	
	private NotificadorSMS() {}
	
	public static NotificadorSMS getInstance() {
		return instance;
	}
	
	@Override
	public void notificarSugerencias(Usuario usuario) {
		
	}

	@Override
	public void notificarAlerta(Usuario usuario) {
		
	}
}
