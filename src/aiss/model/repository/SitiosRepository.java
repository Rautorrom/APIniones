package aiss.model.repository;

import java.util.Collection;

import aiss.model.Sitios;
import aiss.model.Valoraciones;

public interface SitiosRepository {
	
	
	// Valoracion
	public void addValoracion(Valoraciones s);
	public Collection<Valoraciones> getAllValoraciones();
	public Valoraciones getValoracion(String valId);
	public void updateValoracion(Valoraciones s);
	public void deleteValoracion(String valId);
	
	// Sitios
	public void addSitio(Sitios p);
	public Collection<Sitios> getAllSitios();
	public Sitios getSitio(String sitioId);
	public void updateSitio(Sitios p);
	public void deleteSitio(String sitioId);
	public Collection<Valoraciones> getAll(String sitioId);
	public void addValoracion(String sitioId, String songId);
	public void removeValoracion(String sitioId, String valId); 

	
	
	
	

}
