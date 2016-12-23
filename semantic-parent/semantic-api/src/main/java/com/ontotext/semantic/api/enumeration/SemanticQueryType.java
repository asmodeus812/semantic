package com.ontotext.semantic.api.enumeration;

/**
 * Enumeration for the basic SPARQL & Semantic Query types
 * 
 * @author Svetlozar
 */
public enum SemanticQueryType {

	SELECT("SELECT"), DELETE("DELETE"), INSERT("INSERT"), DELETE_DATA("DELETE DATA"), INSERT_DATA("INSERT DATA");

	private final String type;

	private SemanticQueryType(String type) {
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
