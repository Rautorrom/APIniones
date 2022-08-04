package aiss.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import aiss.model.repository.MapSitiosRepository;

public class Sitio {

	private String id;
	private String name;
	private String description;
	private String ciudad;
	private String pagina;
	private Integer tlf;
	private String horario;
	@SuppressWarnings("unused")
	//rating is an unused field as it is calculated from its "valoraciones". Yet, it is declared below for the API to locate the rating field
	//before "valoraciones" and not afterwards.
	private Double rating;
	
	
	public Sitio() {}
	
	public Sitio(String name) {
		this.name = name;
		
	}
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	
	public Integer getTlf() {
		return tlf;
	}

	public void setTlf(Integer numero) {
		this.tlf = numero;
	}
	
	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}
	
	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	
	public Double getRating() {
		List<Valoracion> val = this.getValoraciones();
		if (val==null || val.size()<1) return 0.;
		System.out.println(val.size());
		String res = String.format("%.2f", Double.valueOf(val.stream().map(Valoracion::getEstrellas).reduce(0,(a,b)->a+b))/val.size());
		return Double.valueOf(res);
	}
	
	
	public List<Valoracion> getValoraciones() {
		return MapSitiosRepository.getInstance().getAllValoraciones().stream().filter(v->v.getSitioId().equals(this.id)).collect(Collectors.toList());
	}
	
	public Valoracion getValoracion(String valId) {
		List<Valoracion> val = this.getValoraciones();
		if (val==null)
			return null;
		
		return val.stream().filter(s->s.getId().equals(valId)).findFirst().get();
	}
	
}
