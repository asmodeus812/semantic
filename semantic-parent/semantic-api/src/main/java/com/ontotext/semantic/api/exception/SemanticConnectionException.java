package com.ontotext.semantic.api.exception;

/**
 * Represents a semantic connection exception
 * 
 * @author Svetlozar
 */
public class SemanticConnectionException extends SemanticException {

	private static final long serialVersionUID = 1L;

	/**
	 * Initializes a base semantic connection exception
	 */
	public SemanticConnectionException() {
		super("Unknown Semantic Connection Exception.");
	}

	/**
	 * Initializes complete semantic connection exception
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
	public SemanticConnectionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Initializes a partial semantic connection exception
	 * 
	 * @param message
	 *            the message of the exception
	 * @param cause
	 *            the cause of the exception
	 */
	public SemanticConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Initializes a partial semantic connection exception
	 * 
	 * @param message
	 *            the message of the exception
	 */
	public SemanticConnectionException(String message) {
		super(message);
	}

	/**
	 * Initializes a partial semantic connection exception
	 * 
	 * @param cause
	 *            the cause of the exception
	 */
	public SemanticConnectionException(Throwable cause) {
		super(cause);
	}

}
