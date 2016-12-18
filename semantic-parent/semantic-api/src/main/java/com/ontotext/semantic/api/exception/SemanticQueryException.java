package com.ontotext.semantic.api.exception;

/**
 * Represents a semantic query exception occurring during query evaluation or parsing
 * 
 * @author Svetlozar
 */
public class SemanticQueryException extends SemanticException {

	private static final long serialVersionUID = 1L;

	/**
	 * Initializes a base semantic query exception
	 */
	public SemanticQueryException() {
		super("Unknown Semantic Query Execution Exception.");
	}

	/**
	 * Initializes complete semantic query exception
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
	public SemanticQueryException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Initializes a partial semantic query exception
	 * 
	 * @param message
	 *            the message of the exception
	 * @param cause
	 *            the cause of the exception
	 */
	public SemanticQueryException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Initializes a partial semantic query exception
	 * 
	 * @param message
	 *            the message of the exception
	 */
	public SemanticQueryException(String message) {
		super(message);
	}

	/**
	 * Initializes a partial semantic query exception
	 * 
	 * @param cause
	 *            the cause of the exception
	 */
	public SemanticQueryException(Throwable cause) {
		super(cause);
	}

}
