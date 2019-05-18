package modelo.clima;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AccuWheather implements ProveedorClima{
	private static AccuWheather instancia = new AccuWheather();
	private String path = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/7894";
	private String apiKey = "a9ZEXA1qgqXcX4sIgxv6OXZWV95jOOXC";
	
	private AccuWheather() {}
	
	public List<Clima> obtenerClimas() {
		Gson gson = new Gson();
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(this.path)
				.queryParam("apikey", this.apiKey)
				.queryParam("language", "en-US")
				.queryParam("details", "false")
				.queryParam("metric", "false");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get(Response.class);
		
		List<Clima> pronosticos;
		if(response.getStatus() == 200) 
			pronosticos = gson.fromJson(response.readEntity(String.class), new TypeToken<List<Clima>>(){}.getType());
		else 	
			pronosticos = null;

		return pronosticos;
	}
	
	public static AccuWheather getInstance() {
		return instancia;
	}
}
