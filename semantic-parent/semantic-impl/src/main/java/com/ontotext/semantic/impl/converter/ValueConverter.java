package com.ontotext.semantic.impl.converter;

import java.io.Serializable;

import org.openrdf.model.Literal;

import com.ontotext.semantic.api.common.Converter;

/**
 * Value converter for Literal RDF types
 * 
 * @author Svetlozar
 */
public abstract class ValueConverter implements Converter<Literal> {

	private ValueConverter next;

	/**
	 * Initialize the value converter
	 * 
	 * @param next
	 *            the next converter in the chain
	 */
	public ValueConverter(ValueConverter next) {
		this.next = next;
	}

	@Override
	public Literal convert(Serializable value) {
		// try to convert a value or go to the next converter in the chain if conversion is not successful
		try {
			return convertValue(value.toString());
		} catch (Exception conversionException) {
			return next.convert(value);
		}
	}

	/**
	 * Abstract method used by the converter to determine how to convert the given value
	 * 
	 * @param value
	 *            the value to be converted
	 * @return
	 */
	protected abstract Literal convertValue(String value);
}
