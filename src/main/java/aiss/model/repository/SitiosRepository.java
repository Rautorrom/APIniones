package aiss.model.repository;

import java.util.Collection;

import aiss.model.Sitio;
import aiss.model.Valoracion;

public interface SitiosRepository {
	
	
	// Valoracion
	public void addValoracion(Valoracion s);
	public Collection<Valoracion> getAllValoraciones();
	public Valoracion getValoracion(String valId);
	public void updateValoracion(Valoracion s);
	public void deleteValoracion(String valId);
	
	// Sitios
	public void addSitio(Sitio p);
	public Collection<Sitio> getAllSitios();
	public Sitio getSitio(String sitioId);
	public void updateSitio(Sitio p);
	public void deleteSitio(String sitioId);
	public Collection<Valoracion> getAll(String sitioId);
	public void addValoracionASitio(String sitioId, String songId);
	public void removeValoracion(String sitioId, String valId); 

	
	
	
	

}
