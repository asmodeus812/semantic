package com.ontotext.semantic.impl.common;

import org.openrdf.model.Literal;

import com.ontotext.semantic.api.common.Converter;
import com.ontotext.semantic.impl.converter.ByteConverter;
import com.ontotext.semantic.impl.converter.DoubleConverter;
import com.ontotext.semantic.impl.converter.FloatConverter;
import com.ontotext.semantic.impl.converter.IntegerConverter;
import com.ontotext.semantic.impl.converter.ShortConverter;
import com.ontotext.semantic.impl.converter.StringConverter;

/**
 * Utility class containing all chain converters supported by the semantic engine
 * 
 * @author Svetlozar
 */
public class SemanticChainConverter {

	private SemanticChainConverter() {

	}

	/**
	 * Complete literal chain converter supporting all based types - byte,short,integer,float,double,string
	 */
	public static final Converter<Literal> LITERAL_CHAIN_CONVERTER = new ByteConverter(
			new ShortConverter(
					new IntegerConverter(
							new FloatConverter(
									new DoubleConverter(
											new StringConverter())))));
}
