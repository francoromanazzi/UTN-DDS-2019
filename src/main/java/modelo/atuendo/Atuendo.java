package modelo.atuendo;

import modelo.prenda.Prenda;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "atuendos")
public class Atuendo {
	@Id
	@GeneratedValue
	private Long Id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Prenda parteInferior;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Prenda calzado;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "atuendos_prendasSuperiores",
			joinColumns = {@JoinColumn(name = "atuendo_id")},
			inverseJoinColumns = {@JoinColumn(name = "prenda_superior_id")}
	)
	private List<Prenda> partesSuperiores;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "atuendos_accesorios",
			joinColumns = {@JoinColumn(name = "atuendo_id")},
			inverseJoinColumns = {@JoinColumn(name = "accesorio_id")}
	)
	private List<Prenda> accesorios;

	public Atuendo(List<Prenda> partesSuperiores, Prenda parteInferior, Prenda calzado, List<Prenda> accesorios) {
		this.partesSuperiores = partesSuperiores;
		this.parteInferior = parteInferior;
		this.calzado = calzado;
		this.accesorios = accesorios;
	}

	public Atuendo() {
	}

	public Long getId() {
		return this.Id;
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

	public List<Prenda> obtenerTodasLasPrendas() {
		return Stream.of(partesSuperiores, Collections.singletonList(parteInferior), Collections.singletonList(calzado), accesorios)
				.flatMap(Collection::stream).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "Sup: " + partesSuperiores + ", inf: " + parteInferior + ", calzado: " + calzado + ", acc: " + accesorios;
	}
}
