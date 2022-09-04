package aiss.api.resources;

import java.util.Arrays;


import java.net.URI;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import aiss.model.Sitio;
import aiss.model.Valoracion;
import aiss.model.pokemon.Pokemon;
import aiss.model.repository.MapSitiosRepository;
import aiss.model.repository.SitiosRepository;

@Path("/pokemons")
public class PokemonResource {
	

    protected static PokemonResource instance = null; // La instancia inicialmente no existe, se crea al ejecutar .getInstance().
    final SitiosRepository repository;  // Para poder trabajar con los datos

    private PokemonResource() {
    	repository=MapSitiosRepository.getInstance();
    }

    public static PokemonResource getInstance() {
        instance = (instance == null) ? new PokemonResource() : instance; // Creamos una instancia si no existe.
        return instance;
    }
    
    @Path("/{name}")
    @GET
    @Produces("application/json")
    public Pokemon getPokemon(@PathParam("name") String name)
    {
         return repository.getPokemon(name);
    }
    
    @GET
    @Produces("application/json")
    public Collection<Pokemon> getAllPokemon() {
    	return repository.getAllPokemon();
	} 
    
   
}