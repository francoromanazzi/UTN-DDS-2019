package modelo.sugerencia.decision;

import modelo.sugerencia.Sugerencia;

public abstract class Decision {
	private final Sugerencia sugerencia;

	Decision(Sugerencia sugerencia) {
		this.sugerencia = sugerencia;
	}

	Sugerencia getSugerencia() {
		return sugerencia;
	}

	public abstract void deshacer();
}
