package utils;

import excepciones.ProveedorDeClimaSeCayoException;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public final class HttpRequest {
	public static String pegarleA(WebTarget webTarget) throws ProveedorDeClimaSeCayoException {
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response;

		try {
			response = invocationBuilder.get(Response.class);
		} catch (Exception ex) {
			throw new ProveedorDeClimaSeCayoException();
		}

		if (response.getStatus() != 200) {
			throw new ProveedorDeClimaSeCayoException();
		}

		return response.readEntity(String.class);
	}
}
