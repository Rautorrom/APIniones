package aiss.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import aiss.api.resources.PokemonResource;
import aiss.api.resources.SitioResource;
import aiss.api.resources.UsuarioResource;
import aiss.api.resources.ValoracionResource;


public class APInionesApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	// Loads all resources that are implemented in the application
	// so that they can be found by RESTEasy.
	public APInionesApplication() {

		singletons.add(SitioResource.getInstance());
		singletons.add(ValoracionResource.getInstance());
		singletons.add(UsuarioResource.getInstance());
		singletons.add(PokemonResource.getInstance());
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
