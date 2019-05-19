package modelo.clima.proveedores;

import com.google.gson.Gson;
import excepciones.ProveedorDeClimaSeCayoException;
import modelo.clima.Clima;
import modelo.clima.Meteorologo;

import javax.ws.rs.client.ClientBuilder;
import java.util.List;
import java.util.stream.Collectors;

public class DarkSky extends Meteorologo {
	@Override
	public List<Clima> obtenerPronosticos() throws ProveedorDeClimaSeCayoException {
		String json = pegarleA(ClientBuilder.newClient()
				.target("https://api.darksky.net/forecast/f0e65f5b68e2497562ba72bd1b27dc9a/-34.603928,-58.410690")
				.queryParam("exclude", "daily,flags")
				.queryParam("units", "si"));

		DarkSkyJSONResponse respuestaParseada = new Gson().fromJson(json, DarkSkyJSONResponse.class);
		List<DarkSkyJSON> pronosticosDarkSky = (List) respuestaParseada.getPronosticos();
		return pronosticosDarkSky.stream().map(pronostico -> pronostico.toClima()).collect(Collectors.toList());
	}
}
