package aiss.model;

public class Usuario {
	private String id;
	private String nombre;
	private String apellidos;
	private Integer edad; 
	@SuppressWarnings("unused")
	private Integer numreviews;
	
	
	public Usuario() {}


	public String getUserId() {
		return id;
	}


	public void setUserId(String id) {
		this.id = id;
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
		 return getAllValoraciones().filter(v->v.getUserId.equals(this.userId)).toList();
	}
}
