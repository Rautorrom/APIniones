package aiss.model.repository;	

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.resteasy.spi.NotFoundException;
import org.restlet.resource.ClientResource;

import aiss.model.Sitio;
import aiss.model.Usuario;
import aiss.model.Valoracion;
import aiss.model.pokemon.Pokemon;


public class MapSitiosRepository implements SitiosRepository{

	Map<String, Sitio> sitioMap;
	Map<String, Valoracion> valMap;
	Map<String, Usuario> usMap;
	Map<String, Pokemon> pokeMap;
	private static MapSitiosRepository instance=null;
	private int indexSitio=0;			// Indice para los identificadores de sitios
	private int indexValoracion=0; 		// Indice para los identificadores de valoraciones 
	private int indexUsuario=0;			// Indice para los identificadores de usuario
	
	
	public static MapSitiosRepository getInstance() {
		
		if (instance==null) {
			instance = new MapSitiosRepository();
			instance.init();
		}
		
		return instance;
	}
	
	public void init() {
		
		sitioMap = new HashMap<String,Sitio>();
		valMap = new HashMap<String,Valoracion>();
		usMap= new HashMap<String, Usuario>();
		pokeMap= new HashMap<String, Pokemon>();
		
		//Create Usuarios
		
		Usuario santi = new Usuario();
		santi.setUsername("santi");
		santi.setNombre("Santi");
		santi.setApellidos("Rosado Raya");
		santi.setFechaNacimiento("01/01/2002");
		addUsuario(santi);
		
		Usuario raul = new Usuario();
		raul.setUsername("raul");
		raul.setNombre("Raul");
		raul.setApellidos("Toro Romero");
		raul.setFechaNacimiento("21/08/2001");
		addUsuario(raul);

		Usuario alvaro = new Usuario();
		alvaro.setUsername("Drakerion");
		alvaro.setNombre("Alvaro");
		alvaro.setApellidos("Hidalgo Rodriguez");
		alvaro.setFechaNacimiento("03/07/2000");
		addUsuario(alvaro);
		
		Usuario adrian = new Usuario();
		adrian.setUsername("bloxio3001");
		adrian.setNombre("Adrian");
		adrian.setApellidos("Garcia-Baquero Porras");
		adrian.setFechaNacimiento("15/05/2001");
		addUsuario(adrian);
		
		Usuario laura = new Usuario();
		laura.setUsername("lauriii");
		laura.setNombre("Laura");
		laura.setApellidos("Roldan Merat");
		laura.setFechaNacimiento("12/10/1998");
		addUsuario(laura);
	
			
		// Create sitio
		Sitio playa=new Sitio();
		playa.setName("Playa de Mintonete");
		playa.setDescription("Playa situada en mintonete");
		playa.setCiudad("Mintonete");
		playa.setPagina("https://playademintonete.es");
		playa.setTlf(654321978);
		playa.setHorario("8:00-21:00");
		addSitio(playa);
		
		Sitio discoteca = new Sitio();
		discoteca.setName("Occo");
		discoteca.setDescription("Discoteca de Sevilla");
		discoteca.setCiudad("Sevilla");
		discoteca.setPagina("https://occo.es");
		discoteca.setTlf(987654321);
		discoteca.setHorario("00:00-7:00");
		addSitio(discoteca);
		
		// Create valoracion
		Valoracion one=new Valoracion("s0","u1");
		one.setDescripcion("Me lo pase como nunca");
		one.setFecha(LocalDate.parse("02/05/2018",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		one.setEstrellas(4);
		addValoracion(one);
		
		Valoracion val1=new Valoracion("s1","u2");
		val1.setDescripcion("Increible experiencia pero no repetiría");
		val1.setFecha(LocalDate.parse("03/06/2018",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		val1.setEstrellas(3);
		addValoracion(val1);
		
		Valoracion val2=new Valoracion("s0","u1");
		val2.setDescripcion("No aguanto mas este antro, el trato horrible");
		val2.setFecha(LocalDate.parse("01/04/2018",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		val2.setEstrellas(4);
		addValoracion(val2);
		
		Valoracion val3=new Valoracion("s1","u4");
		val3.setDescripcion("Me encanta este sitio de mis favoritos");
		val3.setFecha(LocalDate.parse("02/12/2015",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		val3.setEstrellas(3);
		addValoracion(val3);
		
		Valoracion gotye=new Valoracion("s1","u5");
		gotye.setDescripcion("Una experiencia insuperable");
		gotye.setFecha(LocalDate.parse("23/02/2002",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		gotye.setEstrellas(2);
		addValoracion(gotye);
	}
		
	// Operaciones relacionadas con los Sitios
	@Override
	public void addSitio(Sitio s) {
		String id = "s" + indexSitio++;	
		s.setId(id);
		sitioMap.put(id,s);
	}
	
	@Override
	public Collection<Sitio> getAllSitios() {
			return sitioMap.values();
	}

	@Override
	public Sitio getSitio(String id) {
		return sitioMap.get(id);
	}
	
	@Override
	public void updateSitio(Sitio p) {
		sitioMap.put(p.getId(),p);
	}

	@Override
	public void deleteSitio(String id) {	
		sitioMap.remove(id);
	}
	

	public Collection<Valoracion> getAllValoraciones(String sitioId) {
		return getSitio(sitioId).getValoraciones();
	}


	
	// Valoraciones related operations
	
	@Override
	public void addValoracion(Valoracion v) {
		String id = "v" + indexValoracion++;
		v.setId(id);
		valMap.put(id, v);
	}
	
	@Override
	public Collection<Valoracion> getAllValoraciones() {
			return valMap.values();
	}

	@Override
	public Valoracion getValoracion(String valId) {
		return valMap.get(valId);
	}

	@Override
	public void updateValoracion(Valoracion s) {
		Valoracion val = valMap.get(s.getId());
		val.setDescripcion(s.getDescripcion());
		val.setFecha(s.getFecha());
		val.setEstrellas(s.getEstrellas());
	}

	@Override
	public void deleteValoracion(String valId) {
		Valoracion val = getValoracion(valId);
		valMap.remove(valId);
	}
	
	// Usuario related operations
	
	@Override
	public void addUsuario(Usuario u) {
		String id = "u" + indexValoracion++;
		u.setUserId(id);
		usMap.put(id, u);
	}
	
	@Override
	public Collection<Usuario> getAllUsuarios() {
			return usMap.values();
	}

	@Override
	public Usuario getUsuario(String usId) {
		return usMap.get(usId);
	}

	@Override
	public void updateUsuario(Usuario u) {
		Usuario us = usMap.get(u.getUserId());
		us.setNombre(u.getNombre());
		us.setApellidos(u.getApellidos());
	}

	@Override
	public void deleteUsuario(String usId) {
		usMap.remove(usId);
	}
	
	
	//Pokemon related operations 
	
	public Pokemon getPokemon(String name) {
        try {
            name = URLEncoder.encode(name, "UTF-8");
        }catch(Exception e){
                throw new NotFoundException("El pokemon con el nombre="+ name +" no ha sido encontrado");    
            }
        String uri = "https://pokemonapiaiss.lm.r.appspot.com/api/pokemons/" + name;
        ClientResource cr = new ClientResource(uri);
        return cr.get(Pokemon.class);
    }
	

	public List<Pokemon> getAllPokemon() {
		String uri = "https://pokemonapiaiss.lm.r.appspot.com/api/pokemons/";
		ClientResource cr = new ClientResource(uri);
		return Arrays.asList(cr.get(Pokemon[].class));
	}
	
	
	
}