package aiss.model.pokemon;

import static org.junit.Assert.*;

import org.junit.Test;

import aiss.model.repository.MapSitiosRepository;

public class PokemonTest {

	static Pokemon pokemon;
	static MapSitiosRepository repositorio = MapSitiosRepository.getInstance();
	
	
	@Test
	public void testGetPokemon() {
		
		String name= "Bulbasaur";
		Pokemon pokemon= repositorio.getPokemon(name);
		
		assertEquals("The id of the Pokemons do not match", pokemon.getName(), name);
		
	}

}
