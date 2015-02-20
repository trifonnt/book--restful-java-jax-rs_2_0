package com.restfully.shop.services;

import javax.ejb.EJBException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Providers;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 */
// @Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {
	@Context
	private Providers providers;

	@SuppressWarnings("unchecked")
	@Override
	public Response toResponse(EJBException exception) {
		if (exception.getCausedByException() == null) {
			return Response.serverError().build();
		}
		@SuppressWarnings("rawtypes")
		Class cause = exception.getCausedByException().getClass();
		@SuppressWarnings("rawtypes")
		ExceptionMapper mapper = providers.getExceptionMapper(cause);
		if (mapper == null) {
			return Response.serverError().build();
		} else {
			return mapper.toResponse(exception.getCausedByException());
		}
	}
}