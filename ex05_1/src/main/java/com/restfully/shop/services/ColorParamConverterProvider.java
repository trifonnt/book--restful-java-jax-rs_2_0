package com.restfully.shop.services;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import com.restfully.shop.domain.ColorEnum;


@Provider
public class ColorParamConverterProvider implements ParamConverterProvider {

	private final ParamConverter<ColorEnum> converter = new ColorConverter();

	public ColorParamConverterProvider() {
		// empty
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if (!rawType.equals(ColorEnum.class)) return null;
		return (ParamConverter<T>) converter;
	}
}