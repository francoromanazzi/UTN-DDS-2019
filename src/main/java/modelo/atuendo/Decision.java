package modelo.atuendo;

public abstract class Decision {
	private final Atuendo atuendo;

	public Decision(Atuendo atuendo) {
		this.atuendo = atuendo;
	}

	public Atuendo getAtuendo() {
		return atuendo;
	}

	public abstract void deshacer();
}
