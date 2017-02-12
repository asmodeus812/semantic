package com.ontotext.semantic.impl.converter;

import static com.ontotext.semantic.core.common.SemanticNamespaceUtil.SHORT_NAMESPACE_SEPARATOR;
import static com.ontotext.semantic.core.common.SemanticNamespaceUtil.findNamespace;

import org.openrdf.model.Namespace;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 * Short URI converter usually located at the head of the chain
 * 
 * @author Svetlozar
 */
public class ShortUriConverter extends ValueConverter<URI> {

	/**
	 * Initialize a short URI converter and the next converter in the chain
	 * 
	 * @param next
	 *            the next converter in the chain
	 */
	public ShortUriConverter(ValueConverter<URI> next) {
		super(next);
	}

	@Override
	protected URI convertValue(String value) {
		if (!value.contains(SHORT_NAMESPACE_SEPARATOR)) {
			throw new IllegalArgumentException("");
		}

		String[] split = value.split(SHORT_NAMESPACE_SEPARATOR);
		Namespace ns = findNamespace(split[0]);
		return new URIImpl(ns.getName() + split[1]);
	}

}
