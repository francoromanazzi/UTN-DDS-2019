package modelo.atuendo;

import modelo.usuario.Usuario;
import modelo.prenda.Prenda;

import java.util.List;

public class Atuendo {
	private final Prenda parteInferior, calzado;
	private final List<Prenda> partesSuperiores, accesorios;
	private EstadoAtuendo estado = EstadoAtuendo.NUEVO;
	private CalificacionAtuendo calificacion;

	public Atuendo(List<Prenda> partesSuperiores, Prenda parteInferior, Prenda calzado, List<Prenda> accesorios) {
		this.partesSuperiores = partesSuperiores;
		this.parteInferior = parteInferior;
		this.calzado = calzado;
		this.accesorios = accesorios;
	}

	public List<Prenda> getPartesSuperiores() {
		return partesSuperiores;
	}

	public Prenda getParteInferior() {
		return parteInferior;
	}

	public Prenda getCalzado() {
		return calzado;
	}

	public List<Prenda> getAccesorios() {
		return accesorios;
	}

	public EstadoAtuendo getEstado() {
		return estado;
	}

	public void setEstado(EstadoAtuendo estado) { this.estado = estado; }

	public CalificacionAtuendo getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(CalificacionAtuendo calificacion) {
		this.calificacion = calificacion;
	}

	public void calificar(CalificacionAtuendo calificacion, Usuario usuario) {
		if(estado == EstadoAtuendo.CALIFICADO)
			usuario.setUltimaDecision(new DecisionRecalificar(this));
		else
			usuario.setUltimaDecision(new DecisionCalificar(this));

		estado = EstadoAtuendo.CALIFICADO;
		this.calificacion = calificacion;
	}

	public void aceptar(Usuario usuario) {
		usuario.setUltimaDecision(new DecisionAceptar(this));
		estado = EstadoAtuendo.ACEPTADO;
	}

	public void rechazar(Usuario usuario) {
		usuario.setUltimaDecision(new DecisionRechazar(this));
		estado = EstadoAtuendo.RECHAZADO;
	}
}
