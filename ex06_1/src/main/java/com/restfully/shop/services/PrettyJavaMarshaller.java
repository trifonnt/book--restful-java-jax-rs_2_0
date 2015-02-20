package com.restfully.shop.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import com.restfully.shop.domain.annotation.Pretty;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @author <a href="mailto:trifont@gmail.com">Trifon Trifonov</a>
 */
@SuppressWarnings("rawtypes")
@Provider
@Produces("application/xml")
@Consumes("application/xml")
public class PrettyJavaMarshaller implements MessageBodyReader, MessageBodyWriter {

	@Context
	protected Providers providers;


	@SuppressWarnings("unchecked")
	@Override
	public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
//		return Serializable.class.isAssignableFrom(type);
		return type.isAnnotationPresent( XmlRootElement.class );
	}

	@Override
	public Object readFrom(Class type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap httpHeaders, InputStream inStream) throws IOException, WebApplicationException
	{
		try {
			JAXBContext ctx = JAXBContext.newInstance( type );
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			return unmarshaller.unmarshal( inStream );
		} catch (JAXBException e) {
			throw new RuntimeException( e );
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return type.isAnnotationPresent( XmlRootElement.class );
	}

	@Override
	public long getSize(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object target, Class type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap httpHeaders, OutputStream outStream) throws IOException,
			WebApplicationException
	{
		try {
			JAXBContext ctx = null;
			ContextResolver<JAXBContext> resolver = providers.getContextResolver(JAXBContext.class, mediaType);
			if (resolver != null) {
				ctx = resolver.getContext( type );
			}
			if (ctx == null) {
				// create one ourselves
				ctx = JAXBContext.newInstance( type );
			}
			Marshaller marshaller = ctx.createMarshaller();

			boolean pretty = false;
			System.err.println("Trifon - target = " + target.toString());
			for (Annotation ann : annotations) {
				System.err.println("Trifon - Annotation = " + ann.toString());
				if (ann.annotationType().equals(Pretty.class)) {
					pretty = true;
					break;
				}
			}
			System.err.println("TRIFON - pretty = " + pretty);
			if (pretty) {
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			}
			marshaller.marshal(target, outStream);
		} catch (JAXBException ex) {
			throw new RuntimeException( ex );
		}
	}
}