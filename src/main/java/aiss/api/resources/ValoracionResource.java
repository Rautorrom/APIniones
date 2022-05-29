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
import javax.ws.rs.core.Response;

import aiss.model.Valoracion;
import aiss.model.repository.MapSitiosRepository;
import aiss.model.repository.SitiosRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;




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
	public Collection<Valoracion> getAll(@QueryParam("autor") String autor,
			@QueryParam("fecha") LocalDate fecha, @QueryParam("rating") Integer rating, @QueryParam("order") String order)
	{
		
		Collection<Valoracion> allValoraciones = repository.getAllValoraciones();
		
		if (autor != null) {
			allValoraciones = allValoraciones.stream().filter(val->val.getAutor()==autor).toList();
		}
		
		if (fecha != null  ) {
			allValoraciones = allValoraciones.stream().filter(val->val.getFecha().compareTo(fecha)>0).toList();
		}
		
		if (rating != null) {
			allValoraciones = allValoraciones.stream().filter(val->val.getEstrellas()==rating).toList();
		}
		
		if (order != null) {
			List<Valoracion> allValoracionesList = ((List<Valoracion>) allValoraciones);
			if (order=="fecha") {
				allValoracionesList.sort(Comparator.comparing(Valoracion::getFecha));
			}
			if (order=="-fecha") {
				 allValoracionesList.sort(Comparator.comparing(Valoracion::getFecha).reversed());
			}
			if (order=="rating") {
				allValoracionesList.sort(Comparator.comparing(Valoracion::getEstrellas));
			}
			if (order=="-rating") {
				allValoracionesList.sort(Comparator.comparing(Valoracion::getEstrellas).reversed());
			}
			allValoraciones = allValoracionesList;
			
		}
		return allValoraciones;
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Valoracion get(@PathParam("id") String valoracionId)
	{
		Valoracion val = repository.getValoracion(valoracionId);
		return val;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addSong(@Context UriInfo uriInfo, Valoracion valoracion) {
		
		return null;
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updateSong(Valoracion song) {
		return null;
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeSong(@PathParam("id") String songId) {
		return null;
	}
	
}
