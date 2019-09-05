package modelo.usuario;

import excepciones.CapacidadExcedidaGuardarropaException;
import excepciones.GuardarropaConMayorPrendasQueCapMaxException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminador")
public abstract class PrivilegiosUsuario {
	@Id	@GeneratedValue
	public long id;
	
	public PrivilegiosUsuario() {}
	
	public abstract void addGuardarropa(Guardarropa guardarropaNuevo, Usuario usuarioNuevo) throws GuardarropaConMayorPrendasQueCapMaxException;

	public abstract boolean admiteAddPrenda(Prenda prendaNueva, Guardarropa guardarropa);

	public abstract void addPrenda(Prenda prendaNueva, Guardarropa guardarropa) throws CapacidadExcedidaGuardarropaException;
}