package com.restfully.shop.features;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;


// @Trifon - not used yet!
@Provider
public class GZIPDecoder implements ReaderInterceptor {

	public Object aroundReadFrom(ReaderInterceptorContext ctx) throws IOException {
		String encoding = ctx.getHeaders().getFirst("Content-Encoding");
		if (!"gzip".equalsIgnoreCase(encoding)) {
			return ctx.proceed();
		}
		GZIPInputStream is = new GZIPInputStream( ctx.getInputStream() );
		ctx.setInputStream( is );

		return ctx.proceed();
	}
}