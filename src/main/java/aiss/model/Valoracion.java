package aiss.model;

import java.time.LocalDate;
import java.util.List;

import aiss.model.repository.MapSitiosRepository;


public class Valoracion {

	private String id;
	private String descripcion;
	private LocalDate fecha;
	private Integer estrellas;
	private Integer likes;
	private String sitioId;
	private String userId;

	public Valoracion(String sitioId, String userId) {
		this.sitioId = sitioId;
		this.userId = userId;
	}

	public Valoracion(String descripcion, LocalDate fecha, Integer estrellas, Integer likes, String sitioId, String userId) {
		if (estrellas<0 || estrellas>5) throw new IllegalArgumentException("Las estrellas han de ser un valor entre 0 y 5");
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.estrellas = estrellas;
		this.likes= likes;
		this.sitioId = sitioId;
		this.userId = userId;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		if (estrellas<0 || estrellas>5) throw new IllegalArgumentException("Las estrellas han de ser un valor entre 0 y 5");
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

	public String getUserId() {
		return userId;
	}

	public Usuario getAutor() {
		return MapSitiosRepository.getInstance().getUsuario(userId);
	}
	
}