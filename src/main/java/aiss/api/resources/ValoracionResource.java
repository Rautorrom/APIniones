package aiss.api.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;

import aiss.model.Valoraciones;
import aiss.model.repository.MapSitiosRepository;
import aiss.model.repository.SitiosRepository;

import java.util.Collection;
// TODO Hay que hacer todo de esta clase(laboratorio 5)


@Path("/valoracion")
public class ValoracionResource {

	public static ValoracionResource _instance=null;
	SitiosRepository repository;
	
	private ValoracionResource(){
		repository=MapSitiosRepository.getInstance();
	}
	
	public static ValoracionResource getInstance()
	{
		if(_instance==null)
			_instance=new ValoracionResource();
		return _instance; 
	}
	
	@GET
	@Produces("application/json")
	public Collection<Valoraciones> getAll()
	{
		return null;
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Valoraciones get(@PathParam("id") String valoracionId)
	{
		return null;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addSong(@Context UriInfo uriInfo, Valoraciones valoracion) {
		
		return null;
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updateSong(Valoraciones song) {
		return null;
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeSong(@PathParam("id") String songId) {
		return null;
	}
	
}
