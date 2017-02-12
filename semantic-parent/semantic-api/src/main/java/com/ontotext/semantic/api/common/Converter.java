package com.ontotext.semantic.api.common;

import java.io.Serializable;

/**
 * Converts a given value to another value T
 * 
 * @param <T>
 *            the value to which to convert
 * @author Svetlozar
 */
public interface Converter<T> {

	/**
	 * Convert the given value to another one
	 * 
	 * @param value
	 *            the value to be converted
	 * @return the converted result
	 */
	public T convert(Serializable value);
}
