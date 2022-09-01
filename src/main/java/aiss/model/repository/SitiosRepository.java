package aiss.model.repository;

import java.util.Collection;

import aiss.model.Sitio;
import aiss.model.Usuario;
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

	// Usuarios
	public void addUsuario(Usuario u);
	public Collection<Usuario> getAllUsuarios();
	public Usuario getUsuario(String usId);
	public void updateUsuario(Usuario u);
	public void deleteUsuario(String usId);
	
	// Pokemon
    public Pokemon getPokemon(String name);
    public Pokemon[] getAllPokemon();
	
}