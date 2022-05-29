package aiss.model;

import java.util.ArrayList;
import java.util.List;

public class Sitio {

	private String id;
	private String name;
	private String description;
	private String ciudad;
	private Double rating;
	private String pagina;
	private Integer tlf;
	private String horario;
	private List<Valoracion> val;
	
	public Sitio() {}
	
	public Sitio(String name) {
		this.name = name;
		
	}
	
	protected void setValoracion(List<Valoracion> s) {
		val = s;
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
		return rating;
	}
	
	public void setRating() {
		Integer suma = 0;
		if (val.isEmpty()) {
			rating = 0.;
		} else {
			for(Valoracion valor : val) {
				suma += valor.getEstrellas();
			}
			rating = (double)suma/val.size();
		}
	}

	
	public List<Valoracion> getValoracion() {
		return val;
	}
	
	public Valoracion getValoracion(String valId) {
		if (val==null)
			return null;
		
		Valoracion song =null;
		for(Valoracion s: val)
			if (s.getId().equals(valId))
			{
				song=s;
				break;
			}
		
		return song;
	}
	
	public void addValoracion(Valoracion s) {
		if (val==null)
			val = new ArrayList<Valoracion>();
		val.add(s);
		setRating();
	}
	
	public void deleteValoracion(Valoracion s) {
		val.remove(s);
		setRating();
	}
	
	public void deleteValoracion(String id) {
		Valoracion s = getValoracion(id);
		if (s!=null)
			val.remove(s);
		setRating();
	}

}
