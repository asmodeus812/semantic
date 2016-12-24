package com.ontotext.semantic.api.enumeration;

/**
 * Enumeration of all base SPARQL query operators.
 * 
 * @author Svetlozar
 */
public enum SemanticQueryOperators {

	WHERE("WHERE"), FILTER("FILTER"), GROUP_BY("GROUP BY"), LIMIT("LIMIT");

	private final String type;

	private SemanticQueryOperators(String type) {
		this.type = type;
	}

	/**
	 * Gets the value of the current operation as a string
	 * 
	 * @return the value of the current operation
	 */
	public String getValue() {
		return this.type;
	}

	@Override
	public String toString() {
		return getValue();
	}
}
