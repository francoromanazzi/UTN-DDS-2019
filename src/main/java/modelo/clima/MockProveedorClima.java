package modelo.clima;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MockProveedorClima implements ProveedorClima{
	private static MockProveedorClima instancia = new MockProveedorClima();
	private String MOCK_ACCUWEATHER_JSON_CLIMA = "[{" +
			       "\"DateTime\":\"2019-05-03T01:00:00-03:00\"," +
			       " \"PrecipitationProbability\": 15," +
			       "\"Temperature\": {\"Value\": 57, \"Unit\": \"F\"}" +
			       "}]";
	private MockProveedorClima() {}
	
	public List<Clima> obtenerClimas() {
		Gson gson = new Gson();
		List<Clima> climas = gson.fromJson(MOCK_ACCUWEATHER_JSON_CLIMA, new TypeToken<List<Clima>>(){}.getType());
		return climas;
	}
	
	public static MockProveedorClima getInstance() {
		return instancia;
	}

}
