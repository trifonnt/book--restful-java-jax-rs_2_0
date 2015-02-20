package com.restfully.shop.services;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import com.restfully.shop.domain.ColorEnum;


@Provider
public class ColorParamConverterProvider2 implements ParamConverterProvider {

//	private final ParamConverter<ColorEnum> converter = new ColorConverter();

	public ColorParamConverterProvider2() {
		// emtpy
	}

	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if (!rawType.equals(ColorEnum.class)) return null;
//		return converter;
		return new ParamConverter<T>() {

            @SuppressWarnings("unchecked")
			@Override
			public T fromString(String value) {
				if (value.equalsIgnoreCase(ColorEnum.BLACK.toString()))
					return (T) ColorEnum.BLACK;
				else if (value.equalsIgnoreCase(ColorEnum.BLUE.toString()))
					return (T) ColorEnum.BLUE;
				else if (value.equalsIgnoreCase(ColorEnum.RED.toString()))
					return (T) ColorEnum.RED;
				else if (value.equalsIgnoreCase(ColorEnum.WHITE.toString()))
					return (T) ColorEnum.WHITE;
				throw new IllegalArgumentException("Invalid color: " + value);
			}

            @Override
			public String toString(T value) {
				return ((ColorEnum)value).toString();
			}
        };
	}
}