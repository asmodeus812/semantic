package com.ontotext.semantic.impl.converter;

import org.openrdf.model.URI;

/**
 * Converts a Null or Invalid URI by returning null as a URI
 * 
 * @author Svetlozar
 */
public class NullUriConverter extends ValueConverter<URI> {

	/**
	 * Initialize a null URI converter
	 */
	public NullUriConverter() {
		super(null);

	}

	@Override
	protected URI convertValue(String value) {
		return null;
	}

}
