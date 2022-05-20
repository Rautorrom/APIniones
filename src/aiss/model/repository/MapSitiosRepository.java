package aiss.model.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import aiss.model.Sitios;
import aiss.model.Valoraciones;


public class MapSitiosRepository implements SitiosRepository{

	Map<String, Sitios> sitioMap;
	Map<String, Valoraciones> valMap;
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
		
		sitioMap = new HashMap<String,Sitios>();
		valMap = new HashMap<String,Valoraciones>();
		
		// Create valoracion
		Valoraciones val1=new Valoraciones();
		val1.setAutor("Raul");
		val1.setDescripcion("Increible experiencia pero no repetiría");
		val1.setFecha(LocalDate.parse("03/06/2018",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		val1.setEstrellas(5);
		val1.setSitioId("p1");
		addValoracion(val1);
		
		Valoraciones one=new Valoraciones();
		one.setAutor("Santi");
		one.setDescripcion("Pille el covid pero no veas como estaba la rubia con la que me lie");
		one.setFecha(LocalDate.parse("02/05/2018",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		one.setEstrellas(4);
		one.setSitioId("p0");
		addValoracion(one);
		
		Valoraciones val2=new Valoraciones();
		val2.setAutor("Alvaro");
		val2.setDescripcion("No aguanto mas este antro, el trato horrible");
		val2.setFecha(LocalDate.parse("01/04/2018",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		val2.setEstrellas(2);
		val2.setSitioId("p0");
		addValoracion(val2);
		
		Valoraciones val3=new Valoraciones();
		val3.setAutor("Adrian");
		val3.setDescripcion("Me encanta este sitio de mis favoritos");
		val3.setFecha(LocalDate.parse("02/12/2015",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		val3.setEstrellas(3);
		val3.setSitioId("p1");
		addValoracion(val3);
		
		Valoraciones gotye=new Valoraciones();
		gotye.setAutor("Laura");
		gotye.setDescripcion("Una experiencia insuperable");
		gotye.setFecha(LocalDate.parse("23/02/2002",DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		gotye.setEstrellas(4);
		gotye.setSitioId("p1");
		addValoracion(gotye);
		
		// Create sitio
		Sitios playa=new Sitios();
		playa.setName("Playa de Mintonete");
		playa.setDescription("Playa situada en mintonete");
		playa.setCiudad("Mintonete");
		playa.setRating();
		playa.setPagina("https://playademintonete.es");
		playa.setNumero(654321978);
		playa.setHorario("8:00-21:00");
		addSitio(playa);
		
		Sitios discoteca = new Sitios();
		discoteca.setName("Occo");
		discoteca.setDescription("Discoteca de Sevilla");
		discoteca.setCiudad("Sevilla");
		discoteca.setRating();
		discoteca.setPagina("https://occo.es");
		discoteca.setNumero(987654321);
		discoteca.setHorario("00:00-7:00");
		addSitio(discoteca);
		
		// Add songs to sitios
		addValoracion(playa.getId(), val1.getId());
		addValoracion(playa.getId(), one.getId());
		addValoracion(playa.getId(), val3.getId());
		addValoracion(playa.getId(), val2.getId());
		
		addValoracion(discoteca.getId(), val2.getId());
		addValoracion(discoteca.getId(), gotye.getId());
	}
	
	// Playlist related operations
	@Override
	public void addSitio(Sitios p) {
		String id = "p" + index++;	
		p.setId(id);
		sitioMap.put(id,p);
	}
	
	@Override
	public Collection<Sitios> getAllSitios() {
			return sitioMap.values();
	}

	@Override
	public Sitios getSitio(String id) {
		return sitioMap.get(id);
	}
	
	@Override
	public void updateSitio(Sitios p) {
		sitioMap.put(p.getId(),p);
	}

	@Override
	public void deleteSitio(String id) {	
		sitioMap.remove(id);
	}
	

	@Override
	public void addValoracion(String playlistId, String songId) {
		Sitios playlist = getSitio(playlistId);
		playlist.addValoracion(valMap.get(songId));
	}

	@Override
	public Collection<Valoraciones> getAll(String playlistId) {
		return getSitio(playlistId).getValoracion();
	}

	@Override
	public void removeValoracion(String playlistId, String valId) {
		getSitio(playlistId).deleteValoracion(valId);
	}

	
	// Song related operations
	
	@Override
	public void addValoracion(Valoraciones s) {
		String id = "s" + index++;
		s.setId(id);
		valMap.put(id, s);
	}
	
	@Override
	public Collection<Valoraciones> getAllValoraciones() {
			return valMap.values();
	}

	@Override
	public Valoraciones getValoracion(String songId) {
		return valMap.get(songId);
	}

	@Override
	public void updateValoracion(Valoraciones s) {
		Valoraciones val = valMap.get(s.getId());
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
