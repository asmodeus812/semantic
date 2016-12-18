package com.ontotext.semantic.api.exception;

/**
 * Represents a semantic repository connection exception
 * 
 * @author Svetlozar
 */
public class SemanticRepositoryException extends SemanticException {

	private static final long serialVersionUID = 1L;

	/**
	 * Initializes a base semantic repository exception
	 */
	public SemanticRepositoryException() {
		super("Unknown Semantic Repository Exception.");
	}

	/**
	 * Initializes complete semantic repository exception
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
	public SemanticRepositoryException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Initializes a partial semantic repository exception
	 * 
	 * @param message
	 *            the message of the exception
	 * @param cause
	 *            the cause of the exception
	 */
	public SemanticRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Initializes a partial semantic repository exception
	 * 
	 * @param message
	 *            the message of the exception
	 */
	public SemanticRepositoryException(String message) {
		super(message);
	}

	/**
	 * Initializes a partial semantic repository exception
	 * 
	 * @param cause
	 *            the cause of the exception
	 */
	public SemanticRepositoryException(Throwable cause) {
		super(cause);
	}

}
