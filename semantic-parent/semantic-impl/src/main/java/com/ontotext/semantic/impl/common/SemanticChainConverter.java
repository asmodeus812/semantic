package com.ontotext.semantic.impl.common;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;

import com.ontotext.semantic.api.common.Converter;
import com.ontotext.semantic.impl.converter.ByteConverter;
import com.ontotext.semantic.impl.converter.DoubleConverter;
import com.ontotext.semantic.impl.converter.FloatConverter;
import com.ontotext.semantic.impl.converter.IntegerConverter;
import com.ontotext.semantic.impl.converter.LongUriConverter;
import com.ontotext.semantic.impl.converter.NullUriConverter;
import com.ontotext.semantic.impl.converter.ShortConverter;
import com.ontotext.semantic.impl.converter.ShortUriConverter;
import com.ontotext.semantic.impl.converter.StringConverter;

/**
 * Utility class containing read built chain converters for values and URIs
 * 
 * @author Svetlozar
 */
public class SemanticChainConverter {

	/**
	 * Complete literal chain converter supporting all basic types - byte, short, integer, float, double, string
	 */
	public static final Converter<Literal> LITERAL_CONVERTER = new ByteConverter(
			new ShortConverter(
					new IntegerConverter(
							new FloatConverter(
									new DoubleConverter(
											new StringConverter())))));

	/**
	 * Complete URI chain converter supporting all basic types - short and long URIs
	 */
	public static final Converter<URI> URI_CONVERTER = new ShortUriConverter(
			new LongUriConverter(new NullUriConverter()));

	/**
	 * Private constructor for utility class
	 */
	private SemanticChainConverter() {

	}
}
