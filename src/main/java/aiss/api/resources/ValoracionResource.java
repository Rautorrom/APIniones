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
	public Collection<Valoracion> getAllValoraciones(@QueryParam("userId") String userId,
										@QueryParam("fecha") String fechaQueried,
										@QueryParam("estrellas") String estrellas, 
										@QueryParam("order") String order,
										@QueryParam("limit") String limitQueried)
	{
		
		
		
		
		List<Valoracion> allValoraciones = repository.getAllValoraciones().stream().collect(Collectors.toList());
	        
        if (userId != null) {
			allValoraciones = allValoraciones.stream().filter(val->val.getUserId().equals(userId)).collect(Collectors.toList());
		}
		
		if (fechaQueried != null  ) {
			LocalDate fecha = LocalDate.parse(fechaQueried, DateTimeFormatter.ofPattern("dd-MM-uuuu"));
			allValoraciones = allValoraciones.stream().filter(val->val.getFecha().compareTo(fecha)>=0).collect(Collectors.toList());
		}
		if (estrellas !=null) {
			if (estrellas.contains("-")) {
				String[] ratingRange = estrellas.split("-");
				Integer minRating = Math.min(Integer.valueOf(ratingRange[0]),Integer.valueOf(ratingRange[1]));
				Integer maxRating = Math.max(Integer.valueOf(ratingRange[0]),Integer.valueOf(ratingRange[1]));
				allValoraciones = allValoraciones.stream().filter(val -> val.getEstrellas()>=minRating && val.getEstrellas()<=maxRating).collect(Collectors.toList());
			} else {
				allValoraciones = allValoraciones.stream().filter(val -> val.getEstrellas()==Integer.valueOf(estrellas)).collect(Collectors.toList());
			}
		}
		
		if (order != null) {
			if (order.equals("autor")) {
				allValoraciones.sort(Comparator.comparing(Valoracion::getAutor));
			}
			if (order.equals("-autor")) {
				 allValoraciones.sort(Comparator.comparing(Valoracion::getAutor).reversed());
			}
			
			if (order.equals("fecha")) {
				allValoraciones.sort(Comparator.comparing(Valoracion::getFecha));
			}
			if (order.equals("-fecha")) {
				 allValoraciones.sort(Comparator.comparing(Valoracion::getFecha).reversed());
			}
			if (order.equals("rating")) {
				allValoraciones.sort(Comparator.comparing(Valoracion::getEstrellas));
			}
			if (order.equals("-rating")) {
				allValoraciones.sort(Comparator.comparing(Valoracion::getEstrellas).reversed());
			}
			
		}
		if (limitQueried!=null) {
			Integer limit = Integer.valueOf(limitQueried);
			limit = limit > allValoraciones.size() ? allValoraciones.size() : limit ;
			allValoraciones = allValoraciones.subList(0, limit);
		}
        return allValoraciones;
	}
	
	
	
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addValoracion(@Context UriInfo uriInfo, Valoracion valoracion) {
		
		if (valoracion.getSitioId() == null)
			throw new BadRequestException("La id del sitio no puede ser nula");
		
		if (repository.getSitio(valoracion.getSitioId())==null)
				throw new BadRequestException("No existe ningún sitio con id: "+valoracion.getSitioId());
		
		if (valoracion.getAutor() == null || valoracion.getAutor().equals(""))
			throw new BadRequestException("El autor no puede estar vacío");
		
		if (valoracion.getDescripcion() == null || valoracion.getDescripcion().equals(""))
			throw new BadRequestException("La descripción no puede estar vacía");

		if (valoracion.getEstrellas() == null)
			throw new BadRequestException("Una valoración no puede no tener estrellas");
		
		valoracion.setFecha(LocalDate.now());
		repository.addValoracion(valoracion);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "getValoracion");
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

		oldValoracion.setFecha(LocalDate.now());
 
		
		return Response.noContent().build();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Valoracion getValoracion(@PathParam("id") String valoracionId)
	{
		Valoracion valoracion = repository.getValoracion(valoracionId);

		if(valoracion==null){
			throw new NotFoundException("La valoracion con id=" + valoracionId + " no se encuentra");			
		}
		
		return valoracion;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteValoracion(@PathParam("id") String valId) {
		repository.deleteValoracion(valId);
		return Response.noContent().build();
	}
	
}
