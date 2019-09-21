package modelo.alerta_meteorologica.accion_ante_alerta_meteorologica;

import excepciones.MensajeriaException;
import modelo.usuario.Usuario;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "RECALCULAR_SUGERENCIA")
public class RecalcularSugerencia extends AccionAnteAlertaMeteorologica {

	@Override
	public void anteLluvia(Usuario usuario) throws MensajeriaException {
		// TODO
	}

	@Override
	public void anteGranizo(Usuario usuario) throws MensajeriaException {
		// TODO
	}

	@Override
	public void anteNieve(Usuario usuario) throws MensajeriaException {
		// TODO
	}
}
