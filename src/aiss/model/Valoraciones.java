package aiss.model;

import java.time.LocalDate;
import java.util.Date;

public class Valoraciones {

	private String id;
	private String autor;
	private String descripcion;
	private LocalDate fecha;
	private Integer estrellas;
	private String sitioId;

	public Valoraciones() {
	}

	public Valoraciones(String autor, String descripcion, LocalDate fecha, Integer estrellas, String sitioId) {
		this.autor = autor;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.estrellas = estrellas;
		this.sitioId = sitioId;
	}
	
	public Valoraciones(String id, String autor, String descripcion, LocalDate fecha, Integer estrellas, String sitioId) {
		this.id=id;
		this.autor = autor;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.estrellas = estrellas;
		this.sitioId = sitioId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Integer getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(Integer estrellas) {
		this.estrellas = estrellas;
	}
	
	public String getSitioId() {
		return sitioId;
	}

	public void setSitioId(String sitioId) {
		this.sitioId = sitioId;
	}

}
