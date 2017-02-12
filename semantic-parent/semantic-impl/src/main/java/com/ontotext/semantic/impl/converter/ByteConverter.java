package com.ontotext.semantic.impl.converter;

import org.openrdf.model.Literal;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 * Byte converter usually located at the head of the chain
 * 
 * @author Svetlozar
 */
public class ByteConverter extends ValueConverter<Literal> {

	/**
	 * Initialize a byte converter and the next converter in the chain
	 * 
	 * @param next
	 *            the next converter in the chain
	 */
	public ByteConverter(ValueConverter<Literal> next) {
		super(next);
	}

	@Override
	public Literal convertValue(String value) {
		Byte singleByte = Byte.valueOf(value);
		return new LiteralImpl(singleByte.toString(), XMLSchema.BYTE);
	}

}
