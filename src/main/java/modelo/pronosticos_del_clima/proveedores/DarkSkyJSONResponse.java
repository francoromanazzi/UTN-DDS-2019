package modelo.pronosticos_del_clima.proveedores;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

class DarkSkyJSONResponse {
	@SerializedName("currently")
	private DarkSkyJSON pronosticoYa;

	@SerializedName("hourly")
	private Hourly hourly;

	private class Hourly {
		@SerializedName("data")
		private Collection<DarkSkyJSON> pronosticos;
	}

	public Collection<DarkSkyJSON> getPronosticos() {
		return Lists.newArrayList(Iterables.concat(hourly.pronosticos, new ArrayList<>(Arrays.asList(pronosticoYa))));
	}
}
