package modelo.alerta_meteorologica.accion_ante_alerta_meteorologica;

import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.usuario.Usuario;

import javax.mail.MessagingException;

interface MedioNotificacion {
	void send(Usuario usuario, AlertaMeteorologica alerta) throws MessagingException;
}
