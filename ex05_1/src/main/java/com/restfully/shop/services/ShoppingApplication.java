package com.restfully.shop.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/services")
public class ShoppingApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();


	public ShoppingApplication() {
		// Resources
		singletons.add(new CustomerResource());
		singletons.add(new CarResource());

		// Providers
		classes.add(ColorParamConverterProvider.class);
//		classes.add(ColorParamConverterProvider2.class);
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