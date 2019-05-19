package modelo.clima.proveedores;

import excepciones.ProveedorDeClimaSeCayoException;
import modelo.clima.Clima;
import modelo.clima.Meteorologo;

import java.util.List;

public class MockProveedorQueFalla extends Meteorologo {
	@Override
	public List<Clima> obtenerPronosticos() throws ProveedorDeClimaSeCayoException {
		throw new ProveedorDeClimaSeCayoException();
	}
}
