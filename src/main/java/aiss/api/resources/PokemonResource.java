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
	
	private String uri = "https://pokemonapiaiss.lm.r.appspot.com/api";
	
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
    @Produces("application/json")
    public Pokemon getPokemon(@PathParam("name") String name)
    {
        try {
            return repository.getPokemon(name);
        }
        catch(Exception e){
            throw new NotFoundException("El pokemon con el nombre="+ name +" no ha sido encontrado");    
        }
    }
    
    @GET
    public Collection<Pokemon> getAllPokemon() {
    	ClientResource cr = null;
		Pokemon [] pokemons = null;
		try {
			cr = new ClientResource(uri);
			pokemons = cr.get(Pokemon[].class);
			
		} catch (ResourceException re) {
			System.err.println("Error when retrieving all songs: " + cr.getResponse().getStatus());
			throw re;
		}
		
		return Arrays.asList(pokemons);
	} 
    
    /*
    @POST
    @Path("/{name}")
    public Response addPokemonComoSitio(Pokemon p) {
    	String id = "s" + indexSitio++;
		Sitio s = new Sitio();
		s.setName(p.getName());
		s.setDescription("Pokemon de la generación "+p.getGeneration()
				+ ", tipo principal "+p.getType1()+" y tipo secundario "+p.getType2()+".");
		s.setId(id);
		sitioMap.put(id,s);
    }
    */
    
   
}