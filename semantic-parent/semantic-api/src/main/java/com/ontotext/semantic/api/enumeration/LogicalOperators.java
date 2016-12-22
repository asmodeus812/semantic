package com.ontotext.semantic.api.enumeration;

/**
 * Enumeration representing all base logical operators
 * 
 * @author Svetlozar
 */
public enum LogicalOperators {

	AND("&&"), OR("||"), XOR("^");

	private final String operation;

	private LogicalOperators(String operation) {
		this.operation = operation;
	}

	/**
	 * Gets the value of the current logical operation as a string
	 * 
	 * @return the value of the current logical operation
	 */
	public String getValue() {
		return this.operation;
	}

	@Override
	public String toString() {
		return getValue();
	}
}
