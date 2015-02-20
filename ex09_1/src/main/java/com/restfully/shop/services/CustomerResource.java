package com.restfully.shop.services;

import com.restfully.shop.domain.Customer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/customers")
public class CustomerResource {
	private Map<Integer, Customer> customerDB = new ConcurrentHashMap<Integer, Customer>();
	private AtomicInteger idCounter = new AtomicInteger();

	public CustomerResource() {
	}

	@POST
	@Consumes("application/xml")
	public Response createCustomer(Customer customer) {
		customer.setId(idCounter.incrementAndGet());
		customerDB.put(customer.getId(), customer);
		System.out.println("Created customer " + customer.getId());
		return Response.created(URI.create("/customers/" + customer.getId()))
				.build();

	}

	@GET
	@Path("{id}")
	@Produces({ "application/xml", "application/json" })
	public Customer getCustomer(@PathParam("id") int id, @Context HttpHeaders headers) {
		Customer customer = customerDB.get(id);
		if (customer == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		System.err.println("headers.getAcceptableMediaTypes() = " + headers.getAcceptableMediaTypes());
		System.err.println("headers.getAcceptableLanguages()  = " + headers.getAcceptableLanguages());
		if (headers.getAcceptableMediaTypes().size() > 0) {
			MediaType type = headers.getAcceptableMediaTypes().get(0);
			Response.ResponseBuilder builder = Response.ok(customer, type);
			
			if (headers.getAcceptableLanguages().size() > 0) {
				Locale language = headers.getAcceptableLanguages().get(0);
				builder.language( language );
			}
//			return builder.build();
		}
		return customer;
	}

	@GET
	@Path("{id}")
	@Produces("text/plain")
	public String getCustomerString(@PathParam("id") int id, @Context HttpHeaders headers) {
		return getCustomer(id, headers).toString();
	}
}