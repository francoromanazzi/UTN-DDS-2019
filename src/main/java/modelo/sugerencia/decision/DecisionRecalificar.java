package modelo.sugerencia.decision;

import modelo.sugerencia.CalificacionSugerencia;
import modelo.sugerencia.EstadoSugerencia;
import modelo.sugerencia.Sugerencia;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "RECALIFICAR")
public class DecisionRecalificar extends Decision {
	@ManyToOne
	private CalificacionSugerencia calificacionAnterior;

	public DecisionRecalificar(Sugerencia sugerencia) {
		super(sugerencia);
		this.calificacionAnterior = sugerencia.getCalificacion();
	}

	public DecisionRecalificar() { }

	public CalificacionSugerencia getCalificacionAnterior() {
		return calificacionAnterior;
	}

	public void setCalificacionAnterior(CalificacionSugerencia calificacionAnterior) {
		this.calificacionAnterior = calificacionAnterior;
	}

	public void deshacer() {
		getSugerencia().setEstado(EstadoSugerencia.CALIFICADO);
		getSugerencia().setCalificacion(calificacionAnterior);
	}
}