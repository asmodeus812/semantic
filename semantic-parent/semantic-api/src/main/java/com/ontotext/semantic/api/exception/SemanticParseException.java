package com.ontotext.semantic.api.exception;

/**
 * Represents a semantic parse exception during query parsing
 * 
 * @author Svetlozar
 */
public class SemanticParseException extends SemanticQueryException {

	private static final long serialVersionUID = 1L;

	/**
	 * Initializes a base semantic parse exception
	 */
	public SemanticParseException() {
		super("Unknown Semantic Evaluation Exception.");
	}

	/**
	 * Initializes complete semantic parse exception
	 * 
	 * @param message
	 *            the message of the exception
	 * @param cause
	 *            the cause of the exception
	 * @param enableSuppression
	 *            should enable suppression
	 * @param writableStackTrace
	 *            should write to stack trace
	 */
	public SemanticParseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Initializes a partial semantic parse exception
	 * 
	 * @param message
	 *            the message of the exception
	 * @param cause
	 *            the cause of the exception
	 */
	public SemanticParseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Initializes a partial semantic parse exception
	 * 
	 * @param message
	 *            the message of the exception
	 */
	public SemanticParseException(String message) {
		super(message);
	}

	/**
	 * Initializes a partial semantic parse exception
	 * 
	 * @param cause
	 *            the cause of the exception
	 */
	public SemanticParseException(Throwable cause) {
		super(cause);
	}

}
