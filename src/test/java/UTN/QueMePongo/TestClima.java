package UTN.QueMePongo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import modelo.clima.Clima;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TestClima {

	private Gson gson = new Gson();
	private String MOCK_ACCUWEATHER_JSON_CLIMA = "{" +
			"\"DateTime\":\"2019-05-03T01:00:00-03:00\"," +
			" \"PrecipitationProbability\": 15," +
			"\"Temperature\": {\"Value\": 57, \"Unit\": \"F\"}" +
			"}";

	@Test
	public void debePoderParsearJsonDeAccuweatherHaciaClima() {
		Clima clima = gson.fromJson(MOCK_ACCUWEATHER_JSON_CLIMA, Clima.class);
		System.out.println(clima.getFecha());
		System.out.println(clima.getProbabilidadPrecipitacion());
		System.out.println(clima.getTemperatura().getUnidad());
		System.out.println(clima.getTemperatura().getValor());
	}

	// @Ignore TODO: Descomentar para cuando no se quiera pegarle a accuweather
	@Test
	public void debePoderPegarleALaAPIDeAccuweather() {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/7894")
				.queryParam("apikey", "a9ZEXA1qgqXcX4sIgxv6OXZWV95jOOXC")
				.queryParam("language", "en-US")
				.queryParam("details", "false")
				.queryParam("metric", "false");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get(Response.class);

		assertEquals(200, response.getStatus());

		List<Clima> pronosticos = gson.fromJson(response.readEntity(String.class), new TypeToken<List<Clima>>(){}.getType());

		assertEquals(12, pronosticos.size());
	}
}
