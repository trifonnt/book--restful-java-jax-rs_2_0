package com.restfully.shop.services;

import com.restfully.shop.domain.Customer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/customers")
public class CustomerResource {
	private Map<Integer, Customer> customerDB = new ConcurrentHashMap<Integer, Customer>();
	private AtomicInteger idCounter = new AtomicInteger();

	public CustomerResource() {
		Customer customer;

		customer = new Customer();
		customer.setId(idCounter.incrementAndGet());
		customer.setFirstName("Bill");
		customer.setLastName("Burke");
		customer.setStreet("263 Clarendon Street");
		customer.setCity("Boston");
		customer.setState("MA");
		customer.setZip("02115");
		customer.setCountry("USA");
		customerDB.put(customer.getId(), customer);
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
	@Produces("application/xml")
	public Response getCustomer(
			  @PathParam("id") int id
			, @HeaderParam("If-None-Match") String sent
			, @Context Request request
	) {
		Customer cust = customerDB.get(id);
		if (cust == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		if (sent == null)
			System.out.println("No If-None-Match sent by client");

		EntityTag tag = new EntityTag(Integer.toString(cust.hashCode()));

		CacheControl cc = new CacheControl();
		cc.setMaxAge(5);

		Response.ResponseBuilder respBulder = request.evaluatePreconditions(tag);
		if (respBulder != null) {
			System.out.println("** revalidation on the server was successful");
			respBulder.cacheControl(cc);
			return respBulder.build();
		}

		// Preconditions not met!
		cust.setLastViewed(new Date().toString());
		respBulder = Response.ok(cust, "application/xml");
		respBulder.cacheControl(cc);
		respBulder.tag(tag);
		return respBulder.build();
	}

	@Path("{id}")
	@PUT
	@Consumes("application/xml")
	public Response updateCustomer(
			  @PathParam("id") int id
			, @Context Request request
			, Customer update
	) {
		Customer cust = customerDB.get(id);
		if (cust == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		EntityTag tag = new EntityTag(Integer.toString(cust.hashCode()));

		Response.ResponseBuilder respBuilder = request.evaluatePreconditions(tag);
		if (respBuilder != null) {
			// Preconditions not met!
			return respBuilder.build();
		}

		// Preconditions met, perform update
		cust.setFirstName(update.getFirstName());
		cust.setLastName(update.getLastName());
		cust.setStreet(update.getStreet());
		cust.setState(update.getState());
		cust.setZip(update.getZip());
		cust.setCountry(update.getCountry());

		respBuilder = Response.noContent();
		return respBuilder.build();
	}
}