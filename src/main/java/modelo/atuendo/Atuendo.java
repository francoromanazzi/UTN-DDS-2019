package modelo.atuendo;

import modelo.prenda.Prenda;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "atuendos")
public class Atuendo {
	@Id @GeneratedValue
	private Long Id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Prenda parteInferior; 
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Prenda calzado;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name="atuendos_prendasSuperiores",
		joinColumns = { @JoinColumn(name = "atuendo_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "prenda_superior_id") }
	)
	private final List<Prenda> partesSuperiores;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name="atuendos_accesorios",
		joinColumns = { @JoinColumn(name = "atuendo_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "accesorio_id") }
	)
	private final List<Prenda> accesorios;

	public Atuendo(List<Prenda> partesSuperiores, Prenda parteInferior, Prenda calzado, List<Prenda> accesorios) {
		this.partesSuperiores = partesSuperiores;
		this.parteInferior = parteInferior;
		this.calzado = calzado;
		this.accesorios = accesorios;
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
