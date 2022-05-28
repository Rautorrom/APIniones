package aiss.api.resources;

import java.net.URI;

import java.util.Collection;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
	public Collection<Sitios> getAll()
	{
		return repository.getAllSitios();
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Sitios get(@PathParam("id") String id)
	{
		Sitios list = repository.getSitio(id);
		
		if (list == null) {
			throw new NotFoundException("The playlist with id="+ id +" was not found");			
		}
		
		return list;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addSitio(@Context UriInfo uriInfo, Sitios sitio) {
		if (sitio.getName() == null || "".equals(sitio.getName()))
			throw new BadRequestException("The name of the playlist must not be null");
		// TODO Auto-generated method stub
		if (sitio.getValoracion()!=null)
			throw new BadRequestException("The songs property is not editable.");

		repository.addSitio(sitio);

		// Builds the response. Returns the playlist the has just been added.
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
			throw new NotFoundException("The playlist with id="+ sitio.getId() +" was not found");			
		}
		
		if (sitio.getValoracion()!=null)
			throw new BadRequestException("The songs property is not editable.");
		
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
			throw new NotFoundException("The playlist with id="+ id +" was not found");
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
			throw new NotFoundException("The playlist with id=" + sitioId + " was not found");
		
		if (val == null)
			throw new NotFoundException("The song with id=" + valId + " was not found");
		
		if (sitio.getValoracion(valId)!=null)
			throw new BadRequestException("The song is already included in the playlist.");
			
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
			throw new NotFoundException("The playlist with id=" + sitioId + " was not found");
		
		if (val == null)
			throw new NotFoundException("The song with id=" + valId + " was not found");
		
		
		repository.removeValoracion(sitioId, valId);		
		
		return Response.noContent().build();
	}
}