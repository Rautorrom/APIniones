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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.model.Sitio;
import aiss.model.Usuario;
import aiss.model.Valoracion;
import aiss.model.repository.MapSitiosRepository;
import aiss.model.repository.SitiosRepository;
@Path("/usuarios")
public class UsuarioResource {
		
		/* Singleton */
		private static UsuarioResource _instance=null;
		SitiosRepository repository;
		
		private UsuarioResource() {
			repository=MapSitiosRepository.getInstance();

		}
		
		public static UsuarioResource getInstance()
		{
			if(_instance==null)
					_instance=new UsuarioResource();
			return _instance;
		}


		@GET
		@Produces("application/json")
		public Collection<Usuario> getAllUsuarios(@QueryParam("limit") String limitQueried, 
										@QueryParam("edad") Integer edad, 
										@QueryParam("nombre") String nombre,
										@QueryParam("order") String order)
		{
			List<Usuario> allUsuarios = repository.getAllUsuarios().stream().collect(Collectors.toList());
			
			if (edad!=null) {
				
				allUsuarios = allUsuarios.stream().filter(usuario -> usuario.getEdad().compareTo(edad)==0).collect(Collectors.toList());
			}
			
			//rating is formatted as 'double-double' for example 0-5, 1.5-3, 5.6-4.2 or as a single double i.e. 3.4...
			//if rating!=null the result must be filtered such as the rating field number must be between those integers or the specified one.
			if (nombre !=null) {
				allUsuarios = allUsuarios.stream().filter(usuario -> usuario.getNombre().toLowerCase().compareTo(nombre.toLowerCase())==0).collect(Collectors.toList());
			}
			
			if (order!=null) {
				
				if (order.equals("nombre")) {
					allUsuarios.sort(Comparator.comparing(Usuario::getNombre));
				}
				if (order.equals("-nombre")) {
					allUsuarios.sort(Comparator.comparing(Usuario::getNombre).reversed());
				}
				if (order.equals("edad")) {
					allUsuarios.sort(Comparator.comparing(Usuario::getEdad));
				}
				if (order.equals("-edad")) {
					allUsuarios.sort(Comparator.comparing(Usuario::getEdad).reversed());
				}
				
			}
			
			
			if (limitQueried!=null) {
				Integer limit = Integer.valueOf(limitQueried);
				limit = limit > allUsuarios.size() ? allUsuarios.size() : limit ;
				allUsuarios = allUsuarios.subList(0, limit);
			}
	 		return allUsuarios;
		}
			
		

		
		@POST
		@Consumes("application/json")
		@Produces("application/json")
		public Response addUsuario(@Context UriInfo uriInfo, Usuario usuario) {
			if (usuario.getNombre() == null || usuario.getNombre().equals(""))
				throw new BadRequestException("El nombre del sitio no puede ser nulo");
			repository.addUsuario(usuario);
			if (usuario.getEdad() <0) throw new BadRequestException("Tlf ha de ser un entero positivo");
			// Builds the response. Returns the place the has just been added.
			UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "getUsuario");
			URI uri = ub.build(usuario.getUserId());
			ResponseBuilder resp = Response.created(uri);
			resp.entity(usuario);			
			return resp.build();
		}
		
		
		@PUT
		@Consumes("application/json")
		public Response updateUsuario(Usuario usuario) {
			Usuario oldUsuario = repository.getUsuario(usuario.getUserId());
			if (oldUsuario == null) {
				throw new NotFoundException("El usuario con el id="+ usuario.getUserId() +" no ha sido encontrado.");			
			}
			
			// Update Nombre
			if (usuario.getNombre()!=null)
				oldUsuario.setNombre(usuario.getNombre());
			
			// Update Apellidos
			if (usuario.getApellidos()!=null)
				oldUsuario.setApellidos(usuario.getApellidos());
			
			// Update Edad
			if (usuario.getEdad()!=null)
				oldUsuario.setEdad(usuario.getEdad());
			
			return Response.noContent().build();
		}
		
		@GET
		@Path("/{id}")
		@Produces("application/json")
		public Usuario getUsuario(@PathParam("id") String id)
		{
	        Usuario usuario = repository.getUsuario(id);
	        return usuario;
		}
		
		@DELETE
		@Path("/{id}")
		public Response deleteUsuario(@PathParam("id") String id) {
			Usuario toBeRemoved=repository.getUsuario(id);
			if (toBeRemoved == null)
				throw new NotFoundException("El usuario con el id="+ id +" no ha sido encontrado.");
			else
				repository.deleteUsuario(id);
			
			return Response.noContent().build();
	}

}
