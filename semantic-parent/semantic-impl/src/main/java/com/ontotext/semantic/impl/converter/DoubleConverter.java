package com.ontotext.semantic.impl.converter;

import org.openrdf.model.Literal;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 * Double converter usually located at the middle of the chain
 * 
 * @author Svetlozar
 */
public class DoubleConverter extends ValueConverter<Literal> {

	/**
	 * Initialize a double converter and the next converter in the chain
	 * 
	 * @param next
	 *            the next converter in the chain
	 */
	public DoubleConverter(ValueConverter<Literal> next) {
		super(next);
	}

	@Override
	public Literal convertValue(String value) {
		Double doubleNumb = Double.valueOf(value);
		return new LiteralImpl(doubleNumb.toString(), XMLSchema.DOUBLE);
	}

}
