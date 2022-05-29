package aiss.model.pokemon;

import static org.junit.Assert.*;

import org.junit.Test;

import aiss.model.repository.MapSitiosRepository;

public class PokemonTest {

	static Pokemon pokemon;
	static MapSitiosRepository repositorio = new MapSitiosRepository();
	
	
	@Test
	public void testGetPokemon() {
		
		String name= "Bulbasur";
		pokemon= repositorio.getPokemonByName(name);
		Pokemon p1 = repositorio.getPokemonByName(pokemon.getName());
		
		assertEquals("The id of the Pokemons do not match", pokemon.getName(), p1.getName());
		assertEquals("The name of the Pokemons do not match", pokemon.getGeneration(), p1.getGeneration());
	
		System.out.println("Pokemon name: " +  p1.getName());
		System.out.println("Pokemon generation: " +  p1.getGeneration());
		
	}

}
