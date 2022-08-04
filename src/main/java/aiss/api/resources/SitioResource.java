package aiss.api.resources;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	public Collection<Sitio> getAllSitios(@QueryParam("limit") String limitQueried, 
									@QueryParam("ciudad") String ciudad, 
									@QueryParam("rating") String rating,
									@QueryParam("order") String order)
	{
		List<Sitio> allSitios = repository.getAllSitios().stream().collect(Collectors.toList());
		
		if (ciudad!=null) {
			
			allSitios = allSitios.stream().filter(sitio -> sitio.getCiudad().toLowerCase().compareTo(ciudad.toLowerCase())==0).collect(Collectors.toList());
		}
		
		//rating is formatted as 'double-double' for example 0-5, 1.5-3, 5.6-4.2 or as a single double i.e. 3.4...
		//if rating!=null the result must be filtered such as the rating field number must be between those integers or the specified one.
		if (rating !=null) {
			if (rating.contains("-")) {
				String[] ratingRange = rating.split("-");
				Double minRating = Math.min(Double.valueOf(ratingRange[0]),Double.valueOf(ratingRange[1]));
				Double maxRating = Math.max(Double.valueOf(ratingRange[0]),Double.valueOf(ratingRange[1]));
				allSitios = allSitios.stream().filter(sitio -> sitio.getRating()>=minRating && sitio.getRating()<=maxRating).collect(Collectors.toList());
			} else {
				allSitios = allSitios.stream().filter(sitio -> sitio.getRating()==Double.valueOf(rating)).collect(Collectors.toList());
			}
			
		}
		
		if (order!=null) {
			
			if (order.equals("nombre")) {
				allSitios.sort(Comparator.comparing(Sitio::getName));
			}
			if (order.equals("-nombre")) {
				allSitios.sort(Comparator.comparing(Sitio::getName).reversed());
			}
			if (order.equals("rating")) {
				allSitios.sort(Comparator.comparing(Sitio::getRating));
			}
			if (order.equals("-rating")) {
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
		
	

	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addSitio(@Context UriInfo uriInfo, Sitio sitio) {
		if (sitio.getName() == null || sitio.getName().equals(""))
			throw new BadRequestException("El nombre del sitio no puede ser nulo");
		repository.addSitio(sitio);
		if (sitio.getTlf() <0) throw new BadRequestException("Tlf ha de ser un entero positivo");
		// Builds the response. Returns the place the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "getSitio");
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
			if (sitio.getTlf() <0) throw new BadRequestException("Tlf ha de ser un entero positivo");
			oldSitio.setTlf(sitio.getTlf());
		
		return Response.noContent().build();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Sitio getSitio(@PathParam("id") String id)
	{
        Sitio sitio = repository.getSitio(id);
        return sitio;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteSitio(@PathParam("id") String id) {
		Sitio toBeRemoved=repository.getSitio(id);
		if (toBeRemoved == null)
			throw new NotFoundException("El sitio con el id="+ id +" no ha sido encontrado.");
		else
			repository.getAllValoraciones(id).forEach(v->repository.deleteValoracion(v.getId()));
			repository.deleteSitio(id);
		
		return Response.noContent().build();
	}
	
	@GET
	@Path("/{id}/valoraciones")
	@Produces("application/json")
	public List<Valoracion> getValoraciones(@PathParam("id") String id, @QueryParam("autor") String autor,
			@QueryParam("fecha") String fechaQueried,
			@QueryParam("estrellas") String estrellas, 
			@QueryParam("order") String order,
			@QueryParam("limit") String limitQueried)
	{
        Sitio sitio = repository.getSitio(id);
        List<Valoracion> allValoraciones = sitio.getValoraciones();
        
        if (autor != null) {
			allValoraciones = allValoraciones.stream().filter(val->val.getAutor().toLowerCase().compareTo(autor.toLowerCase())==0).collect(Collectors.toList());
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
	@Path("/{id}/valoraciones")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addValoracion(@Context UriInfo uriInfo, @PathParam("id") String sitioId, Valoracion valoracion) {

		if (valoracion.getSitioId()==null || valoracion.getSitioId()=="") valoracion.setSitioId(sitioId);
		if (!valoracion.getSitioId().equals(sitioId)) throw new BadRequestException("Estás intentando guardar una valoración perteneciente a un id "
				+ "distinto al que has especificado en la ruta. Para añadir la valoración a este sitio especifica la id correcta en el body o no especifiques ninguna ("
				+ "se asignará la id del sitio en el path automáticamente). Para añadir la valoración a otro sitio haz el POST a /sitios/:sitioId donde :sitioId es el id "
				+ "del sitio al que deseas agregar la valoración, o haz el POST directamente a /valoraciones.");
		
		if (valoracion.getAutor() == null || valoracion.getAutor().equals(""))
			throw new BadRequestException("El autor no puede estar vacío");
		
		if (valoracion.getDescripcion() == null || valoracion.getDescripcion().equals(""))
			throw new BadRequestException("La descripción no puede estar vacía");

		if (valoracion.getEstrellas() == null)
			throw new BadRequestException("Una valoración no puede no tener estrellas");
		
		valoracion.setFecha(LocalDate.now());
		
		repository.addValoracion(valoracion);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(ValoracionResource.class, "getValoracion");
		URI uri = ub.build(valoracion.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(valoracion);			
		return resp.build();
	}
	@PUT
	@Path("/{id}/valoraciones")
	@Consumes("application/json")
	public Response updateValoracion(@PathParam("id") String sitioId, Valoracion valoracion) {
		
		Valoracion oldValoracion = repository.getValoracion(valoracion.getId());
		
		if (oldValoracion == null) {
			throw new NotFoundException("La valoración con id="+ valoracion.getId() +" no se encuentra");
		}
		
		if (!oldValoracion.getSitioId().equals(sitioId)) throw new BadRequestException("Estás intentando editar una valoración perteneciente a un id "
				+ "distinto al que has especificado en la ruta. Para editar la valoración a otro sitio haz el PUT a /sitios/:sitioId donde :sitioId es el id "
				+ "del sitio al que está adjunto la valoración que deseas editar, o haz el PUT directamente a /valoraciones.");
		
		if (valoracion.getAutor()!=null) 
			oldValoracion.setAutor(valoracion.getAutor());
		
		if (valoracion.getDescripcion()!=null)
			oldValoracion.setDescripcion(valoracion.getDescripcion());

		if (valoracion.getEstrellas()!=null)
			oldValoracion.setEstrellas(valoracion.getEstrellas());

		oldValoracion.setFecha(LocalDate.now());
 
		
		return Response.noContent().build();
	}
	
	
}