package modelo.clima;

import excepciones.ProveedorDeClimaSeCayoException;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public abstract class Meteorologo {
	public abstract List<Clima> obtenerPronosticos();

	protected String pegarleA(WebTarget webTarget) throws ProveedorDeClimaSeCayoException {
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response;

		try {
			response = invocationBuilder.get(Response.class);
		}
		catch(Exception ex) {
			throw new ProveedorDeClimaSeCayoException();
		}

		if(response.getStatus() != 200) {
			throw new ProveedorDeClimaSeCayoException();
		}

		return response.readEntity(String.class);
	}
}
