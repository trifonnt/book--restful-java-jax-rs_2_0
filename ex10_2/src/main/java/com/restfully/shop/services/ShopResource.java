package com.restfully.shop.services;

import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.net.URI;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 */
@Path("/shop")
public class ShopResource {

	@HEAD
	public Response head(@Context UriInfo uriInfo) {
		UriBuilder absoluteUriBuilder = uriInfo.getBaseUriBuilder();

		URI customerUrl = absoluteUriBuilder.clone().path(CustomerResource.class).build();
		URI orderUrl = absoluteUriBuilder.clone().path(OrderResource.class).build();

		Response.ResponseBuilder responseBuilder = Response.ok();
		Link customers = Link.fromUri(customerUrl).rel("customers").type("application/xml").build();
		Link orders = Link.fromUri(orderUrl).rel("orders").type("application/xml").build();
		responseBuilder.links(customers, orders);
		return responseBuilder.build();
	}

	@GET
	public Response get(@Context UriInfo uriInfo) {
		return head( uriInfo );
	}
}