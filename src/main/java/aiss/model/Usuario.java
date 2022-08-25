package aiss.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public class Usuario implements Comparable<Usuario>{
	private String userId;
	private String username;
	private String nombre;
	private String apellidos;
	private LocalDate fechaNacimiento; 
	@SuppressWarnings("unused")
	private Integer numreviews;
	
	
	public Usuario() {}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
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


	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public Integer getEdad() {
		return this.fechaNacimiento.until(LocalDate.now()).getYears();
	}
	
	public Integer getNumvaloraciones() {
		 return aiss.model.repository.MapSitiosRepository.getInstance().getAllValoraciones().stream().filter(v->v.getUserId().equals(this.userId))
				 .collect(Collectors.toList()).size();
	}
	
	


	@Override
	public int compareTo(Usuario o) {
		return this.nombre.compareTo(o.getNombre());
	}
}
