package aiss.model;

import java.util.Collection;
import java.util.stream.Collectors;

public class Usuario {
	private String userId;
	private String nombre;
	private String apellidos;
	private Integer edad; 
	@SuppressWarnings("unused")
	private Integer numreviews;
	
	
	public Usuario() {}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public Integer getEdad() {
		return edad;
	}


	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public Integer getNumvaloraciones() {
		 return aiss.model.repository.MapSitiosRepository.getInstance().getAllValoraciones().stream().filter(v->v.getUserId().equals(this.userId))
				 .collect(Collectors.toList()).size();
	}
}
