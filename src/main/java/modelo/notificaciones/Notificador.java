package modelo.notificaciones;

import modelo.usuario.Usuario;

public interface Notificador {
	public void notificarSugerencias(Usuario usuario);
	public void notificarAlerta(Usuario usuario);
}
