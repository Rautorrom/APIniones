package aiss.api.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.model.Sitio;
import aiss.model.Valoracion;
import aiss.model.repository.MapSitiosRepository;
import aiss.model.repository.SitiosRepository;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Path("/valoraciones")
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
	public Collection<Valoracion> getAllValoraciones(@QueryParam("autor") String autor,
										@QueryParam("fecha") String fechaQueried,
										@QueryParam("rating") String rating, 
										@QueryParam("order") String order)
	{
		
		
		
		
		List<Valoracion> allValoraciones = repository.getAllValoraciones().stream().collect(Collectors.toList());
				
		if (autor != null) {
			allValoraciones = allValoraciones.stream().filter(val->val.getAutor().toLowerCase().compareTo(autor.toLowerCase())==0).collect(Collectors.toList());
		}
		
		if (fechaQueried != null  ) {
			LocalDate fecha = LocalDate.parse(fechaQueried, DateTimeFormatter.ofPattern("dd-MM-uuuu"));
			allValoraciones = allValoraciones.stream().filter(val->val.getFecha().compareTo(fecha)>0).collect(Collectors.toList());
		}
		if (rating !=null) {
			String[] ratingRange = rating.split("-");
			Double minRating = Math.min(Double.valueOf(ratingRange[0]),Double.valueOf(ratingRange[1]));
			Double maxRating = Math.max(Double.valueOf(ratingRange[0]),Double.valueOf(ratingRange[1]));
			allValoraciones = allValoraciones.stream().filter(val -> val.getEstrellas()>minRating && val.getEstrellas()<maxRating).collect(Collectors.toList());
		}
		
		if (order != null) {
			if (order.compareTo("autor")==0) {
				allValoraciones.sort(Comparator.comparing(Valoracion::getAutor));
			}
			if (order.compareTo("-autor")==0) {
				 allValoraciones.sort(Comparator.comparing(Valoracion::getAutor).reversed());
			}
			
			if (order.compareTo("fecha")==0) {
				allValoraciones.sort(Comparator.comparing(Valoracion::getFecha));
			}
			if (order.compareTo("-fecha")==0) {
				 allValoraciones.sort(Comparator.comparing(Valoracion::getFecha).reversed());
			}
			if (order.compareTo("rating")==0) {
				allValoraciones.sort(Comparator.comparing(Valoracion::getEstrellas));
			}
			if (order.compareTo("-rating")==0) {
				allValoraciones.sort(Comparator.comparing(Valoracion::getEstrellas).reversed());
			}
			
			
		}
		return allValoraciones;
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Valoracion getValoracion(@PathParam("id") String valoracionId)
	{
		Valoracion valoracion = repository.getValoracion("v"+valoracionId);

		if(valoracion==null){
			throw new NotFoundException("La valoracion con id=" + valoracionId + " no se encuentra");			
		}
		
		return valoracion;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addValoracion(@Context UriInfo uriInfo, Valoracion valoracion) {
		
		if (valoracion.getAutor() == null || "".equals(valoracion.getAutor()))
			throw new BadRequestException("El autor no puede estar vacío");
		
		if (valoracion.getDescripcion() == null || "".equals(valoracion.getDescripcion()))
			throw new BadRequestException("La valoración no puede estar vacía");

		if (valoracion.getEstrellas() == null)
			throw new BadRequestException("Una valoración no puede no tener estrellas");
		
		repository.addValoracion(valoracion);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(valoracion.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(valoracion);			
		return resp.build();
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updateValoracion(Valoracion valoracion) {
		
		Valoracion oldValoracion = repository.getValoracion(valoracion.getId());
		
		if (oldValoracion == null) {
			throw new NotFoundException("La valoración con id="+ valoracion.getId() +" no se encuentra");
		}

		if (valoracion.getDescripcion()!=null)
			oldValoracion.setDescripcion(valoracion.getDescripcion());

		if (valoracion.getEstrellas()!=null)
			oldValoracion.setEstrellas(valoracion.getEstrellas());

		if (valoracion.getFecha()!=null)
			oldValoracion.setFecha(valoracion.getFecha());

		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteValoracion(@PathParam("id") String valId) {
		repository.deleteValoracion("v"+valId);
		return Response.noContent().build();
	}
	
}
