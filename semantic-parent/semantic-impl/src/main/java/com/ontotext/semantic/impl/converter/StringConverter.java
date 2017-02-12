package com.ontotext.semantic.impl.converter;

import org.openrdf.model.Literal;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 * String converter usually located at the bottom of the convert chain
 * 
 * @author Svetlozar
 */
public class StringConverter extends ValueConverter {

	/**
	 * Initialize a string converter
	 */
	public StringConverter() {
		super(null);
	}

	@Override
	public Literal convertValue(String value) {
		return new LiteralImpl(value, XMLSchema.STRING);
	}

}
