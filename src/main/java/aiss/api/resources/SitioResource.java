package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

import aiss.model.Sitios;
import aiss.model.Valoraciones;
import aiss.model.repository.MapSitiosRepository;
import aiss.model.repository.SitiosRepository;






@Path("/lists")
public class SitioResource {
	
	/* Singleton */
	private static SitioResource _instance=null;
	SitiosRepository repository;
	
	private SitioResource() {
		repository=MapSitiosRepository.getInstance();

	}
	
	public static SitioResource getInstance()
	{
		if(_instance==null)
				_instance=new SitioResource();
		return _instance;
	}


	@GET
	@Produces("application/json")
	public Response getAll(@QueryParam("limit") String limit, @QueryParam("fields") String fields)
	{
		Collection<Sitios> result = new ArrayList<>();
		result = repository.getAllSitios();
		
		
		return Response.ok(result).build();
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") String id, @QueryParam("fields") String fields)
	{
        Sitios sitio = repository.getSitio(id);
        return Response.ok(sitio).build();
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addSitio(@Context UriInfo uriInfo, Sitios sitio) {
		if (sitio.getName() == null || "".equals(sitio.getName()))
			throw new BadRequestException("The name of the place must not be null");

		repository.addSitio(sitio);

		// Builds the response. Returns the place the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(sitio.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(sitio);			
		return resp.build();
	}

	
	@PUT
	@Consumes("application/json")
	public Response updateSitio(Sitios sitio) {
		Sitios oldsitio = repository.getSitio(sitio.getId());
		if (oldsitio == null) {
			throw new NotFoundException("The place with id="+ sitio.getId() +" was not found");			
		}
		
		if (sitio.getValoracion()!=null)
			throw new BadRequestException("The reviews property is not editable.");
		
		// Update name
		if (sitio.getName()!=null)
			oldsitio.setName(sitio.getName());
		
		// Update description
		if (sitio.getDescription()!=null)
			oldsitio.setDescription(sitio.getDescription());
		// TODO Auto-generated method stub
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeSitio(@PathParam("id") String id) {
		Sitios toberemoved=repository.getSitio(id);
		if (toberemoved == null)
			throw new NotFoundException("The place with id="+ id +" was not found");
		else
			repository.deleteSitio(id);
		
		return Response.noContent().build();
	}
	
	
	@POST	
	@Path("/{sitioId}/{valId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addValoracion(@Context UriInfo uriInfo,@PathParam("playlistId") String sitioId, @PathParam("songId") String valId)
	{				
		
		Sitios sitio = repository.getSitio(sitioId);
		Valoraciones val = repository.getValoracion(valId);
		
		if (sitio==null)
			throw new NotFoundException("The place with id=" + sitioId + " was not found");
		
		if (val == null)
			throw new NotFoundException("The review with id=" + valId + " was not found");
		
		if (sitio.getValoracion(valId)!=null)
			throw new BadRequestException("The review is already included for this place.");
			
		repository.addValoracion(sitioId, valId);		

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(sitioId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(sitio);			
		return resp.build();
	}
	
	
	@DELETE
	@Path("/{sitioId}/{valId}")
	public Response removeValoracion(@PathParam("playlistId") String sitioId, @PathParam("songId") String valId) {
		Sitios sitio = repository.getSitio(sitioId);
		Valoraciones val = repository.getValoracion(valId);
		
		if (sitio==null)
			throw new NotFoundException("The place with id=" + sitioId + " was not found");
		
		if (val == null)
			throw new NotFoundException("The review with id=" + valId + " was not found");
		
		
		repository.removeValoracion(sitioId, valId);		
		
		return Response.noContent().build();
	}
}