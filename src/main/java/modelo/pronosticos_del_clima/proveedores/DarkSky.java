package modelo.pronosticos_del_clima.proveedores;

import com.google.gson.Gson;
import excepciones.ProveedorDeClimaSeCayoException;
import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.pronosticos_del_clima.Meteorologo;
import modelo.pronosticos_del_clima.Pronostico;
import utils.HttpRequest;

import javax.ws.rs.client.ClientBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DarkSky extends Meteorologo {
	@Override
	public List<Pronostico> obtenerPronosticos(Optional<Consumer<List<Pronostico>>> callback) throws ProveedorDeClimaSeCayoException {
		String json = HttpRequest.pegarleA(ClientBuilder.newClient()
				.target("https://api.darksky.net/forecast/f0e65f5b68e2497562ba72bd1b27dc9a/-34.603928,-58.410690")
				.queryParam("exclude", "daily,flags,currently")
				.queryParam("units", "si"));

		DarkSkyJSONResponse respuestaParseada = new Gson().fromJson(json, DarkSkyJSONResponse.class);
		Collection<DarkSkyJSON> pronosticosDarkSky = respuestaParseada.getPronosticos();
		List<Pronostico> ret = pronosticosDarkSky.stream().map(DarkSkyJSON::toPronostico).collect(Collectors.toList());
		callback.ifPresent(cb -> cb.accept(ret));
		return ret;
	}

	@Override
	public List<AlertaMeteorologica> obtenerAlertasMeteorologicas() {
		return null;
	}
}
