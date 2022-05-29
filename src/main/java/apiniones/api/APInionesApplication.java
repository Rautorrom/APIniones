package apiniones.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import apiniones.api.resources.SitioResource;
import apiniones.api.resources.ValoracionResource;


public class APInionesApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	// Loads all resources that are implemented in the application
	// so that they can be found by RESTEasy.
	public APInionesApplication() {

		singletons.add(SitioResource.getInstance());
		singletons.add(ValoracionResource.getInstance());
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
