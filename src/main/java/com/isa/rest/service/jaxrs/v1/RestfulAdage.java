package com.isa.rest.service.jaxrs.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

// The Application class's name should be unique if multiple
// Applications are to be deployed into the same servlet container.
@ApplicationPath("/resourcesA")
public class RestfulAdage extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> set = new HashSet<>();
		set.add(Adages.class);
		return set;
	}
}