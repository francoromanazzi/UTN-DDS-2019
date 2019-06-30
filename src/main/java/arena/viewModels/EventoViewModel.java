package arena.viewModels;
import org.uqbar.commons.model.annotations.Observable;

@Observable
public class EventoViewModel {
	public String nombre;
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
