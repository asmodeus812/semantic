package com.ontotext.semantic.impl.converter;

import org.openrdf.model.Literal;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 * Integer converter usually located at the middle of the chain
 * 
 * @author Svetlozar
 */
public class IntegerConverter extends ValueConverter {

	/**
	 * Initialize an integer converter and the next converter in the chain
	 * 
	 * @param next
	 *            the next converter in the chain
	 */
	public IntegerConverter(ValueConverter next) {
		super(next);
	}

	@Override
	public Literal convertValue(String value) {
		Integer integer = Integer.valueOf(value);
		return new LiteralImpl(integer.toString(), XMLSchema.INTEGER);
	}

}
