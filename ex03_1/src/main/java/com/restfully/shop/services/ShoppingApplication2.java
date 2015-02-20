package com.restfully.shop.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.restfully.shop.services.v1.CustomerResource;

import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/services2")
public class ShoppingApplication2 extends Application {

	private Set<Object> singletons = new HashSet<Object>();


	public ShoppingApplication2() {
		singletons.add(new CustomerResource());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}