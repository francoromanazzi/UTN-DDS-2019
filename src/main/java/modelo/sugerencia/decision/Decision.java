package modelo.sugerencia.decision;

import modelo.sugerencia.Sugerencia;

public abstract class Decision {
	private final Sugerencia sugerencia;

	public Decision(Sugerencia sugerencia) {
		this.sugerencia = sugerencia;
	}

	public Sugerencia getSugerencia() {
		return sugerencia;
	}

	public abstract void deshacer();
}
