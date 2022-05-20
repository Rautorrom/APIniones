package aiss.model;

import java.util.ArrayList;
import java.util.List;

public class Sitios {

	private String id;
	private String name;
	private String description;
	private String ciudad;
	private Double rating;
	private String pagina;
	private Integer numero;
	private String horario;
	private List<Valoraciones> val;
	
	public Sitios() {}
	
	public Sitios(String name) {
		this.name = name;
	}
	
	protected void setValoracion(List<Valoraciones> s) {
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
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
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
		for(Valoraciones valor : val) {
			suma += valor.getEstrellas();
		}
		rating = (double)suma/val.size();
	}

	
	public List<Valoraciones> getValoracion() {
		return val;
	}
	
	public Valoraciones getValoracion(String valId) {
		if (val==null)
			return null;
		
		Valoraciones song =null;
		for(Valoraciones s: val)
			if (s.getId().equals(valId))
			{
				song=s;
				break;
			}
		
		return song;
	}
	
	public void addValoracion(Valoraciones s) {
		if (val==null)
			val = new ArrayList<Valoraciones>();
		val.add(s);
		setRating();
	}
	
	public void deleteValoracion(Valoraciones s) {
		val.remove(s);
		setRating();
	}
	
	public void deleteValoracion(String id) {
		Valoraciones s = getValoracion(id);
		if (s!=null)
			val.remove(s);
		setRating();
	}

}
