package modelo.alerta_meteorologica.accion_ante_alerta_meteorologica;

import excepciones.MensajeriaException;
import modelo.usuario.Usuario;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_accion")
@Table(name = "acciones_ante_alertas_meteorologicas")
public abstract class AccionAnteAlertaMeteorologica {
	@Id
	@GeneratedValue
	public long Id;

	public abstract void anteLluvia(Usuario usuario) throws MensajeriaException;

	public abstract void anteGranizo(Usuario usuario) throws MensajeriaException;

	public abstract void anteNieve(Usuario usuario) throws MensajeriaException;
}
