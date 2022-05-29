package aiss.model;

import java.time.LocalDate;
import java.util.List;


public class Valoracion {

	private String id;
	private String autor;
	private String descripcion;
	private LocalDate fecha;
	private Integer estrellas;
	private Integer likes;
	private String sitioId;


	public Valoracion() {
	}

	public Valoracion(String autor, String descripcion, LocalDate fecha, Integer estrellas, Integer likes, String sitioId) {
		this.autor = autor;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.estrellas = estrellas;
		this.likes= likes;
		this.sitioId = sitioId;
	}
	
	public Valoracion(String id, String autor, String descripcion, LocalDate fecha, Integer estrellas, Integer likes, String sitioId) {
		this.id=id;
		this.autor = autor;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.estrellas = estrellas;
		this.likes= likes;
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
	
	public Integer getLikes() {
		if (likes==null) return 0;
		return likes;
	}
	
	public void incrementLikes(String usuarioId){
		likes++;
	}
	
	public void decrementLikes(String usuarioId){
		likes--;
	}
	
	public String getSitioId() {
		return sitioId;
	}


	public void setSitioId(String sitioId) {
		this.sitioId = sitioId;
	}
}