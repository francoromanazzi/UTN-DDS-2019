package modelo.alerta_meteorologica.accion_ante_alerta_meteorologica;

import excepciones.MensajeriaException;
import modelo.usuario.Usuario;
import utils.SmsSender;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "RECIBIR_SMS")
public class RecibirSMS extends AccionAnteAlertaMeteorologica {

	@Override
	public void anteLluvia(Usuario usuario) throws MensajeriaException {
		SmsSender.send(usuario.getNumeroTelefono(), "El servicio de Que Me Pongo le avisa que va a llover");
	}

	@Override
	public void anteGranizo(Usuario usuario) throws MensajeriaException {
		SmsSender.send(usuario.getNumeroTelefono(), "El servicio de Que Me Pongo le avisa que va a granizar");
	}

	@Override
	public void anteNieve(Usuario usuario) throws MensajeriaException {
		SmsSender.send(usuario.getNumeroTelefono(), "El servicio de Que Me Pongo le avisa que va a nevar");
	}
}
