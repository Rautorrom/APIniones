package aiss.model.repository;

import java.util.Collection;

import aiss.model.Sitio;
import aiss.model.Valoracion;
import aiss.model.pokemon.Pokemon;

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
	public Collection<Valoracion> getAllValoraciones(String sitioId);
	public void addValoracionASitio(String sitioId, String songId);
	public void deleteValoracionfromSitio(String sitioId, String valId); 
	
	// Pokemon
	public Pokemon[] getAllPokemon() ;
    public Pokemon getPokemon(Integer pId);
	

}
