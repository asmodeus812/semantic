package com.ontotext.semantic.core.common;

import org.openrdf.model.BNode;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

/**
 * Semantic instance utility class
 * 
 * @author Svetlozar
 */
public class SemanticInstanceUtil {

	/**
	 * Private constructor for utility class
	 */
	private SemanticInstanceUtil() {

	}

	/**
	 * Checks if a given value is an instance (property or an object) or a literal, true for instance or false for
	 * literal or BNode
	 * 
	 * @param value
	 *            the value to be checked
	 * @return true if instance (property or an object), false otherwise
	 */
	public static boolean isValueInstance(Value value) {
		return (value instanceof URI) || (value instanceof BNode);
	}

	/**
	 * Extracts the actual value from an URI, BNode or a Literal using the super interface Value
	 * 
	 * @param value
	 *            the value from which to extract
	 * @return the extracted value as a string
	 */
	public static String extractActualValue(Value value) {
		if (value instanceof URI) {
			return ((URI) value).getLocalName();
		} else if (value instanceof BNode) {
			return ((BNode) value).getID();
		} else {
			return ((Literal) value).getLabel();
		}
	}

}
