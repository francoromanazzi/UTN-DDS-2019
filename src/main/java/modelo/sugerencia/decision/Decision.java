package modelo.sugerencia.decision;

import modelo.sugerencia.Sugerencia;

import javax.persistence.*;

@Entity
@Table(name = "decisiones")
@DiscriminatorColumn(name = "tipo_decision")
public abstract class Decision {
	@Id @GeneratedValue
	private Long id;

	@ManyToOne
	private Sugerencia sugerencia;

	Decision(Sugerencia sugerencia) {
		this.sugerencia = sugerencia;
	}

	public Decision() { }

	Sugerencia getSugerencia() {
		return sugerencia;
	}

	public void setSugerencia(Sugerencia sugerencia) {
		this.sugerencia = sugerencia;
	}

	public abstract void deshacer();
}
