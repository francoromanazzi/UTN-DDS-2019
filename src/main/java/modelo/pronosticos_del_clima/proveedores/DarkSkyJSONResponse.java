package modelo.pronosticos_del_clima.proveedores;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;

public class DarkSkyJSONResponse {
	@SerializedName("hourly")
	private Hourly hourly;

	private class Hourly {
		@SerializedName("data")
		private List<DarkSkyJSON> pronosticos;
	}

	public List<DarkSkyJSON> getPronosticos() {
		return Lists.newArrayList(Iterables.concat(hourly.pronosticos));
	}
}
