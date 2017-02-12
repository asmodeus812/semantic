package com.ontotext.semantic.impl.converter;

import org.openrdf.model.Literal;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 * Float converter usually located at the middle of the chain
 * 
 * @author Svetlozar
 */
public class FloatConverter extends ValueConverter {

	/**
	 * Initialize a float converter and the next converter in the chain
	 * 
	 * @param next
	 *            the next converter in the chain
	 */
	public FloatConverter(ValueConverter next) {
		super(next);
	}

	@Override
	public Literal convertValue(String value) {
		Float floatNumb = Float.valueOf(value);
		return new LiteralImpl(floatNumb.toString(), XMLSchema.FLOAT);
	}

}
