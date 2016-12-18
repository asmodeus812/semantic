package com.ontotext.semantic.api.exception;

/**
 * Represents a semantic evaluation exception during query evaluation
 * 
 * @author Svetlozar
 */
public class SemanticEvaluateException extends SemanticQueryException {

	private static final long serialVersionUID = 1L;

	/**
	 * Initializes a base semantic evaluate exception
	 */
	public SemanticEvaluateException() {
		super("Unknown Semantic Evaluation Exception.");
	}

	/**
	 * Initializes complete semantic evaluate exception
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
	public SemanticEvaluateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Initializes a partial semantic evaluate exception
	 * 
	 * @param message
	 *            the message of the exception
	 * @param cause
	 *            the cause of the exception
	 */
	public SemanticEvaluateException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Initializes a partial semantic evaluate exception
	 * 
	 * @param message
	 *            the message of the exception
	 */
	public SemanticEvaluateException(String message) {
		super(message);
	}

	/**
	 * Initializes a partial semantic evaluate exception
	 * 
	 * @param cause
	 *            the cause of the exception
	 */
	public SemanticEvaluateException(Throwable cause) {
		super(cause);
	}

}
