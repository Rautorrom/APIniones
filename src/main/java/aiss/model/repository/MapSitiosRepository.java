package aiss.model.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import aiss.model.Sitio;
import aiss.model.Valoracion;


public class MapSitiosRepository implements SitiosRepository{

	Map<String, Sitio> sitioMap;
	Map<String, Valoracion> valMap;
	private static MapSitiosRepository instance=null;
	private int index=0;			// Index to create playlists and songs' identifiers.
	
	
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
		
		// Create valoracion
		Valoracion val1=new Valoracion();
		val1.setAutor("Raul");
		val1.setDescripcion("Increible experiencia pero no repetir�a");
		val1.setFecha(LocalDate.parse("03/06/2018",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		val1.setEstrellas(5);
		val1.setSitioId("p1");
		addValoracion(val1);
		
		Valoracion one=new Valoracion();
		one.setAutor("Santi");
		one.setDescripcion("Pille el covid pero no veas como estaba la rubia con la que me lie");
		one.setFecha(LocalDate.parse("02/05/2018",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		one.setEstrellas(4);
		one.setSitioId("p0");
		addValoracion(one);
		
		Valoracion val2=new Valoracion();
		val2.setAutor("Alvaro");
		val2.setDescripcion("No aguanto mas este antro, el trato horrible");
		val2.setFecha(LocalDate.parse("01/04/2018",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		val2.setEstrellas(2);
		val2.setSitioId("p0");
		addValoracion(val2);
		
		Valoracion val3=new Valoracion();
		val3.setAutor("Adrian");
		val3.setDescripcion("Me encanta este sitio de mis favoritos");
		val3.setFecha(LocalDate.parse("02/12/2015",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		val3.setEstrellas(3);
		val3.setSitioId("p1");
		addValoracion(val3);
		
		Valoracion gotye=new Valoracion();
		gotye.setAutor("Laura");
		gotye.setDescripcion("Una experiencia insuperable");
		gotye.setFecha(LocalDate.parse("23/02/2002",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		gotye.setEstrellas(4);
		gotye.setSitioId("p1");
		addValoracion(gotye);
		
		// Create sitio
		Sitio playa=new Sitio();
		playa.setName("Playa de Mintonete");
		playa.setDescription("Playa situada en mintonete");
		playa.setCiudad("Mintonete");
		playa.setRating();
		playa.setPagina("https://playademintonete.es");
		playa.setTlf(654321978);
		playa.setHorario("8:00-21:00");
		addSitio(playa);
		
		Sitio discoteca = new Sitio();
		discoteca.setName("Occo");
		discoteca.setDescription("Discoteca de Sevilla");
		discoteca.setCiudad("Sevilla");
		discoteca.setRating();
		discoteca.setPagina("https://occo.es");
		discoteca.setTlf(987654321);
		discoteca.setHorario("00:00-7:00");
		addSitio(discoteca);
		
		// Añade valoraciones a los sitios
		addValoracion(playa.getId(), val1.getId());
		addValoracion(playa.getId(), one.getId());
		addValoracion(playa.getId(), val3.getId());
		addValoracion(playa.getId(), val2.getId());
		
		addValoracion(discoteca.getId(), val2.getId());
		addValoracion(discoteca.getId(), gotye.getId());
	}
	
	// Operaciones relacionadas con los Sitios
	@Override
	public void addSitio(Sitio s) {
		String id = "s" + index++;	
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
	

	@Override
	public void addValoracion(String playlistId, String songId) {
		Sitio playlist = getSitio(playlistId);
		playlist.addValoracion(valMap.get(songId));
	}

	@Override
	public Collection<Valoracion> getAll(String playlistId) {
		return getSitio(playlistId).getValoracion();
	}

	@Override
	public void removeValoracion(String playlistId, String valId) {
		getSitio(playlistId).deleteValoracion(valId);
	}

	
	// Valoraciones related operations
	
	@Override
	public void addValoracion(Valoracion v) {
		String id = "v" + index++;
		v.setId(id);
		valMap.put(id, v);
		addValoracion(v.getSitioId(), id);
	}
	
	@Override
	public Collection<Valoracion> getAllValoraciones() {
			return valMap.values();
	}

	@Override
	public Valoracion getValoracion(String songId) {
		return valMap.get(songId);
	}

	@Override
	public void updateValoracion(Valoracion s) {
		Valoracion val = valMap.get(s.getId());
		val.setAutor(s.getAutor());
		val.setDescripcion(s.getDescripcion());
		val.setFecha(s.getFecha());
		val.setEstrellas(s.getEstrellas());
		val.setSitioId(s.getSitioId());
	}

	@Override
	public void deleteValoracion(String valId) {
		valMap.remove(valId);
	}
	
}
