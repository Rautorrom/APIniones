package aiss.api.resources;

import java.util.Arrays;

import aiss.model.repository.MapSitiosRepository;
import aiss.model.repository.SitiosRepository;

public class PokemonResource {

    protected static PokemonResource instance = null; // La instancia inicialmente no existe, se crea al ejecutar .getInstance().
    final SitiosRepository repository;  // Para poder trabajar con los datos

    private PokemonResource() {
    	repository=MapSitiosRepository.getInstance();
    }

    public static PokemonResource getInstance() {
        instance = (instance == null) ? new PokemonResource() : instance; // Creamos una instancia si no existe.
        return instance;
    }
    
    
    @GET
    public Response getPokemon() {
        List<Map<String, Object>> tasks;
        try {
            tasks = Arrays.asList(repository.getAllPokemons())
            		.stream()
            		.map(pokemon -> Parse.taskFromPokemon(pokemon, null, null, null).getFields(Task.ALL_ATTRIBUTES))
            		.collect(Collectors.toList());
        } catch (Exception e) {
            return Message.send(Response.Status.NOT_FOUND, Pair.of("status: ", "404"),
                    Pair.of("message: ", "The pokemon with the name was not found"));
        }
        return Response.ok(tasks).build();
    }
    
    
    
    
    
}