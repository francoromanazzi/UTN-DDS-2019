package modelo.sugerencia;

import modelo.atuendo.Atuendo;
import modelo.sugerencia.decision.DecisionAceptar;
import modelo.sugerencia.decision.DecisionCalificar;
import modelo.sugerencia.decision.DecisionRecalificar;
import modelo.sugerencia.decision.DecisionRechazar;
import modelo.usuario.Usuario;

public class Sugerencia {
	private final Atuendo atuendo;
	private EstadoSugerencia estado = EstadoSugerencia.NUEVO;
	private CalificacionSugerencia calificacion;

	public Sugerencia(Atuendo atuendo) {
		this.atuendo = atuendo;
	}

	public Atuendo getAtuendo() {
		return atuendo;
	}

	public EstadoSugerencia getEstado() {
		return estado;
	}

	public void setEstado(EstadoSugerencia estado) { this.estado = estado; }

	public CalificacionSugerencia getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(CalificacionSugerencia calificacion) {
		this.calificacion = calificacion;
	}

	public void calificar(CalificacionSugerencia calificacion, Usuario usuario) {
		if(estado == EstadoSugerencia.CALIFICADO)
			usuario.setUltimaDecision(new DecisionRecalificar(this));
		else
			usuario.setUltimaDecision(new DecisionCalificar(this));

		estado = EstadoSugerencia.CALIFICADO;
		this.calificacion = calificacion;
	}

	public void aceptar(Usuario usuario) {
		usuario.setUltimaDecision(new DecisionAceptar(this));
		estado = EstadoSugerencia.ACEPTADO;
	}

	public void rechazar(Usuario usuario) {
		usuario.setUltimaDecision(new DecisionRechazar(this));
		estado = EstadoSugerencia.RECHAZADO;
	}
}
