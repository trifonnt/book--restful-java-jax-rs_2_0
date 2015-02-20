package com.restfully.shop.services;

import javax.ws.rs.ext.ParamConverter;

import com.restfully.shop.domain.ColorEnum;


public class ColorConverter implements ParamConverter<ColorEnum> {

	@Override
	public ColorEnum fromString(String value) {
		if (value.equalsIgnoreCase(ColorEnum.BLACK.toString()))
			return ColorEnum.BLACK;
		else if (value.equalsIgnoreCase(ColorEnum.BLUE.toString()))
			return ColorEnum.BLUE;
		else if (value.equalsIgnoreCase(ColorEnum.RED.toString()))
			return ColorEnum.RED;
		else if (value.equalsIgnoreCase(ColorEnum.WHITE.toString()))
			return ColorEnum.WHITE;
		throw new IllegalArgumentException("Invalid color: " + value);
	}

	public String toString(ColorEnum value) {
		return value.toString();
	}
}