package modelo.clima.proveedores;

import com.google.gson.Gson;
import modelo.clima.Clima;
import modelo.clima.Meteorologo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

public class DarkSky implements Meteorologo {
	public List<Clima> obtenerPronosticos() {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("https://api.darksky.net/forecast/f0e65f5b68e2497562ba72bd1b27dc9a/-34.603928,-58.410690")
				.queryParam("exclude", "daily,flags")
				.queryParam("units", "si");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get(Response.class);

		Gson gson = new Gson();
		String json = response.readEntity(String.class);
		DarkSkyJSONResponse respuestaParseada = gson.fromJson(json, DarkSkyJSONResponse.class);
		List<DarkSkyJSON> pronosticosDarkSky = (List) respuestaParseada.getPronosticos();
		List<Clima> pronosticosClima = pronosticosDarkSky.stream().map(pronostico -> pronostico.toClima()).collect(Collectors.toList());
		return pronosticosClima;
	}
}
