package com.ontotext.semantic.impl.converter;

import org.openrdf.model.Literal;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 * Short converter usually located at the head of the chain
 * 
 * @author Svetlozar
 */
public class ShortConverter extends ValueConverter {

	/**
	 * Initialize a short converter and the next converter in the chain
	 * 
	 * @param next
	 *            the next converter in the chain
	 */
	public ShortConverter(ValueConverter next) {
		super(next);
	}

	@Override
	public Literal convertValue(String value) {
		Short shortInt = Short.valueOf(value);
		return new LiteralImpl(shortInt.toString(), XMLSchema.SHORT);
	}

}
