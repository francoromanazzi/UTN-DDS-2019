package modelo.usuario;

import excepciones.CapacidadExcedidaGuardarropaException;
import excepciones.GuardarropaConMayorPrendasQueCapMaxException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminador")
@Table(name = "privilegios_usuario")
public abstract class PrivilegioUsuario {
	@Id
	@GeneratedValue
	public long Id;

	public PrivilegioUsuario() {
	}

	public abstract void addGuardarropa(Guardarropa guardarropaNuevo, Usuario usuarioNuevo) throws GuardarropaConMayorPrendasQueCapMaxException;

	protected abstract boolean admiteAddPrenda(Prenda prendaNueva, Guardarropa guardarropa);

	public abstract void addPrenda(Prenda prendaNueva, Guardarropa guardarropa) throws CapacidadExcedidaGuardarropaException;
}