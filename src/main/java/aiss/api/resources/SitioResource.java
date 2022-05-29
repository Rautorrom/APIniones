package aiss.api.resources;

import java.net.URI;
import java.util.Collection;
import java.util.Comparator;
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

import aiss.model.Sitio;
import aiss.model.Valoracion;
import aiss.model.repository.MapSitiosRepository;
import aiss.model.repository.SitiosRepository;


@Path("/sitios")
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
	public Collection<Sitio> getAll(@QueryParam("limit") String limitQueried, 
									@QueryParam("ciudad") String ciudad, 
									@QueryParam("rating") String rating,
									@QueryParam("order") String order)
	{
		List<Sitio> allSitios = repository.getAllSitios().stream().collect(Collectors.toList());
		
		if (ciudad!=null) {
			
			allSitios = allSitios.stream().filter(sitio -> sitio.getCiudad().toLowerCase().compareTo(ciudad.toLowerCase())==0).collect(Collectors.toList());
		}
		
		//rating is formatted as 'double-double' for example 0-5, 1.5-3, 5.6-4.2 .
		//if rating!=null the result must be filtered such as the rating field number must be between those integers.
		if (rating !=null) {
			String[] ratingRange = rating.split("-");
			Double minRating = Math.min(Double.valueOf(ratingRange[0]),Double.valueOf(ratingRange[1]));
			Double maxRating = Math.max(Double.valueOf(ratingRange[0]),Double.valueOf(ratingRange[1]));
			allSitios = allSitios.stream().filter(sitio -> sitio.getRating()>minRating && sitio.getRating()<maxRating).collect(Collectors.toList());
		}
		
		if (order!=null) {
			
			if (order.compareTo("name") == 0) {
				allSitios.sort(Comparator.comparing(Sitio::getName));
			}
			if (order.compareTo("-name") == 0) {
				allSitios.sort(Comparator.comparing(Sitio::getName).reversed());
			}
			if (order.compareTo("rating") == 0) {
				allSitios.sort(Comparator.comparing(Sitio::getRating));
			}
			if (order.compareTo("-rating") == 0) {
				allSitios.sort(Comparator.comparing(Sitio::getRating).reversed());
			}
			
		}
		
		
		if (limitQueried!=null) {
			Integer limit = Integer.valueOf(limitQueried);
			limit = limit > allSitios.size() ? allSitios.size() : limit ;
			allSitios = allSitios.subList(0, limit);
		}
 		return allSitios;
	}
		
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Sitio get(@PathParam("id") String id)
	{
        Sitio sitio = repository.getSitio("s"+id);
        return sitio;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addSitio(@Context UriInfo uriInfo, Sitio sitio) {
		if (sitio.getName() == null || sitio.getName().equals(""))
			throw new BadRequestException("El nombre del sitio no puede ser nulo");
		
		if (sitio.getValoracion()!=null)
			throw new BadRequestException("Error con las valoraciones del sitio");

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
	public Response updateSitio(Sitio sitio) {
		Sitio oldSitio = repository.getSitio(sitio.getId());
		if (oldSitio == null) {
			throw new NotFoundException("El sitio con el id="+ sitio.getId() +" no ha sido encontrado.");			
		}
		
		if (sitio.getId()!=null)
			throw new BadRequestException("La Id no es editable.");
		
		if (sitio.getValoracion()!=null)
			throw new BadRequestException("Las valoraciones no son editables.");
		
		// Update name
		if (sitio.getName()!=null)
			oldSitio.setName(sitio.getName());
		
		// Update ciudad
		if (sitio.getCiudad()!=null)
			oldSitio.setCiudad(sitio.getCiudad());
		
		// Update descripcion
		if (sitio.getDescription()!=null)
			oldSitio.setDescription(sitio.getDescription());
		
		// Update horario
		if (sitio.getHorario()!=null)
			oldSitio.setHorario(sitio.getHorario());
		
		// Update pagina
		if (sitio.getPagina()!=null)
			oldSitio.setPagina(sitio.getPagina());
		
		// Update telephone
		if (sitio.getTlf()!=null)
			oldSitio.setTlf(sitio.getTlf());
		
		return Response.noContent().build();
	}
	 
	@DELETE
	@Path("/{id}")
	public Response removeSitio(@PathParam("id") String id) {
		Sitio toBeRemoved=repository.getSitio(id);
		if (toBeRemoved == null)
			throw new NotFoundException("El sitio con el id="+ id +" no ha sido encontrado.");
		else
			repository.deleteSitio(id);
		
		return Response.noContent().build();
	}
	
	
	@POST	
	@Path("/{sitioId}/{valId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addValoracion(@Context UriInfo uriInfo,@PathParam("sitioId") String sitioId, @PathParam("valId") String valId)
	{				
		
		Sitio sitio = repository.getSitio(sitioId);
		Valoracion val = repository.getValoracion(valId);
		
		if (sitio==null)
			throw new NotFoundException("El sitio con el id="+ sitioId +" no ha sido encontrado.");
		
		if (val == null)
			throw new NotFoundException("La valoración con el id="+ valId +" no ha sido encontrada.");
		
		if (sitio.getValoracion(valId)!=null)
			throw new BadRequestException("La valoración ya está incluida en este sitio.");
			
		repository.addValoracionASitio(val.getSitioId(), valId);		

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
		Sitio sitio = repository.getSitio(sitioId);
		Valoracion val = repository.getValoracion(valId);
		
		if (sitio==null)
			throw new NotFoundException("El sitio con el id="+ sitioId +" no ha sido encontrado.");
		
		if (val == null)
			throw new NotFoundException("La valoración con el id="+ valId +" no ha sido encontrada.");
		
		
		repository.removeValoracion(sitioId, valId);		
		
		return Response.noContent().build();
	}
}