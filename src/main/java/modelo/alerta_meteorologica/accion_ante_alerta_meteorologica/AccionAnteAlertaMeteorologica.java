package modelo.alerta_meteorologica.accion_ante_alerta_meteorologica;

import modelo.usuario.Usuario;

import javax.mail.MessagingException;

public interface AccionAnteAlertaMeteorologica {
	void anteLluvia(Usuario usuario) throws MessagingException;

	void anteGranizo(Usuario usuario) throws MessagingException;
}
