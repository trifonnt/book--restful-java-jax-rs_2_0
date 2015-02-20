package com.restfully.shop.services;

import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 */
@Path("/shop")
public interface IStoreResource {
	@HEAD
	Response head(@Context UriInfo uriInfo);
}