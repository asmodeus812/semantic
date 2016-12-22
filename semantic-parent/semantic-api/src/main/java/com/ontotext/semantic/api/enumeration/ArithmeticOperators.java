package com.ontotext.semantic.api.enumeration;

/**
 * Enumerated representation of all basic arithmetic operators and operations
 * 
 * @author Svetlozar
 */
public enum ArithmeticOperators {

	EQUALS("="), DOES_NOT_EQUAL("!="), GREATER(">"), GREATER_OR_EQUAL(">="), LESS("<"), LESS_OR_EQUAL(
			"<="), ADD("+"), SUB("-"), DIV("/"), MUL("*");

	private final String operation;

	private ArithmeticOperators(String operation) {
		this.operation = operation;
	}

	/**
	 * Gets the value of the current operation as a string
	 * 
	 * @return the value of the current operation
	 */
	public String getValue() {
		return this.operation;
	}

	@Override
	public String toString() {
		return getValue();
	}
}
