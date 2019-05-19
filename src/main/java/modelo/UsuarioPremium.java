package modelo;

public class UsuarioPremium extends Usuario{
	
	@Override
	public void addGuardarropa(Guardarropa guardarropa) {
		guardarropa.setCapacidadMaxima(Integer.MAX_VALUE);
		this.guardarropas.add(guardarropa);
	}
}
