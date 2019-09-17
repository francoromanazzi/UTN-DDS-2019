package modelo.sugerencia.decision;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "VACIO")
public class DecisionVacia extends Decision {
	public DecisionVacia() {
		super(null);
	}

	public void deshacer() {

	}
}
