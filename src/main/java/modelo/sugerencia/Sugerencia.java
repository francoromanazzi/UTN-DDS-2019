package modelo.sugerencia;

import modelo.atuendo.Atuendo;
import modelo.sugerencia.decision.DecisionAceptar;
import modelo.sugerencia.decision.DecisionCalificar;
import modelo.sugerencia.decision.DecisionRecalificar;
import modelo.sugerencia.decision.DecisionRechazar;
import modelo.usuario.Usuario;

import javax.persistence.*;

@Entity
@Table(name = "sugerencias")
public class Sugerencia {

	@Id
	@GeneratedValue
	private Long Id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Atuendo atuendo;

	@Enumerated(EnumType.STRING)
	private EstadoSugerencia estado = EstadoSugerencia.NUEVO;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private CalificacionSugerencia calificacion;

	public Sugerencia() {
	}

	public Sugerencia(Atuendo atuendo) {
		this.atuendo = atuendo;
	}

	public Long getId() {
		return this.Id;
	}

	public Atuendo getAtuendo() {
		return atuendo;
	}

	public EstadoSugerencia getEstado() {
		return estado;
	}

	public void setEstado(EstadoSugerencia estado) {
		this.estado = estado;
	}

	public CalificacionSugerencia getCalificacion() {
		return this.calificacion;
	}

	public void setCalificacion(CalificacionSugerencia calificacion) {
		this.calificacion = calificacion;
	}

	public void calificar(CalificacionSugerencia calificacion, Usuario usuario) {
		if (estado == EstadoSugerencia.CALIFICADO)
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
