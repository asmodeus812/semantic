package com.ontotext.semantic.api.exception;

/**
 * Represents a generic semantic runtime exception
 * 
 * @author Svetlozar
 */
public class SemanticException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Initializes a base semantic exception
	 */
	public SemanticException() {
		super("Unknown Semantic Exception.");
	}

	/**
	 * Initializes complete semantic exception
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
	public SemanticException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Initializes a partial semantic exception
	 * 
	 * @param message
	 *            the message of the exception
	 * @param cause
	 *            the cause of the exception
	 */
	public SemanticException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Initializes a partial semantic exception
	 * 
	 * @param message
	 *            the message of the exception
	 */
	public SemanticException(String message) {
		super(message);
	}

	/**
	 * Initializes a partial semantic exception
	 * 
	 * @param cause
	 *            the cause of the exception
	 */
	public SemanticException(Throwable cause) {
		super(cause);
	}

}
