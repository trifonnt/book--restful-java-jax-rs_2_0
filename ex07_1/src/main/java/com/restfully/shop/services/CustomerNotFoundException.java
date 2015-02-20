package com.restfully.shop.services;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 */
public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public CustomerNotFoundException(String s) {
		super(s);
	}
}