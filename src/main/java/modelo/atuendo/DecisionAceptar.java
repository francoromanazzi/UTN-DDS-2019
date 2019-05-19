package modelo.atuendo;

public class DecisionAceptar extends Decision {
	public DecisionAceptar(Atuendo atuendo) {
		super(atuendo);
	}

	public void deshacer() {
		getAtuendo().setEstado(EstadoAtuendo.NUEVO);
	}
}
