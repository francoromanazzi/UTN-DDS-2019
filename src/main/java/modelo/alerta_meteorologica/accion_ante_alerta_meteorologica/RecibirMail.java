package modelo.alerta_meteorologica.accion_ante_alerta_meteorologica;

import excepciones.MensajeriaException;
import modelo.usuario.Usuario;
import utils.MailSender;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "RECIBIR_MAIL")
public class RecibirMail extends AccionAnteAlertaMeteorologica {

	@Override
	public void anteLluvia(Usuario usuario) throws MensajeriaException {
		MailSender.send(usuario.getMail(), "Alerta de lluvia", "El servicio de Que Me Pongo le avisa que va a llover");

	}

	@Override
	public void anteGranizo(Usuario usuario) throws MensajeriaException {
		MailSender.send(usuario.getMail(), "Alerta de granizo", "El servicio de Que Me Pongo le avisa que va a granizar");
	}

	@Override
	public void anteNieve(Usuario usuario) throws MensajeriaException {
		MailSender.send(usuario.getMail(), "Alerta de nieve", "El servicio de Que Me Pongo le avisa que va a nevar");
	}
}
