package aiss.model.pokemon;

import static org.junit.Assert.*;

import org.junit.Test;

import aiss.model.repository.MapSitiosRepository;
import aiss.model.pokemon.Pokemon;


public class PokemonTest {

	static Pokemon pokemon1;
	static Pokemon[] pokeAll;
	static MapSitiosRepository repositorio = new MapSitiosRepository();
	
	
	@Test
	public void testGetPokemon() {
		
		String nombre1= "Pikachu";
		pokemon1= repositorio.getPokemon(nombre1);
		Pokemon p = repositorio.getPokemon(pokemon1.getName());
		
		assertEquals("The id of the Pokemons do not match", pokemon1.getName(), p.getName());
		assertEquals("The name of the Pokemons do not match", pokemon1.getGeneration(), p.getGeneration());
		
		
		//Enseñar resultado
		System.out.println("Nombre pokemon: " +  p.getName());
		System.out.println("Generacion del pokemon: " +  p.getGeneration());
	}
} 