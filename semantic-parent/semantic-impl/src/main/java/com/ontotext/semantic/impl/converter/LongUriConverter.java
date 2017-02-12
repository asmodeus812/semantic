package com.ontotext.semantic.impl.converter;

import static com.ontotext.semantic.core.common.SemanticNamespaceUtil.LONG_NAMESPACE_SEPARATOR;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.ANGLE_BRACE_CLOSE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.ANGLE_BRACE_OPEN;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.EMPTY_STRING;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 * Converts a long URIs to a URI RDF object
 * 
 * @author Svetlozar
 */
public class LongUriConverter extends ValueConverter<URI> {

	/**
	 * Initialize a long URI converter and the next converter in the chain
	 * 
	 * @param next
	 *            the next converter in the chain
	 */
	public LongUriConverter(ValueConverter<URI> next) {
		super(next);
	}

	@Override
	protected URI convertValue(String value) {
		if (!value.contains(LONG_NAMESPACE_SEPARATOR)) {
			throw new IllegalArgumentException();
		}

		// Remove braces if present
		String modified = value.replace(ANGLE_BRACE_OPEN, EMPTY_STRING);
		modified = modified.replace(ANGLE_BRACE_CLOSE, EMPTY_STRING);

		return new URIImpl(modified);
	}

}
