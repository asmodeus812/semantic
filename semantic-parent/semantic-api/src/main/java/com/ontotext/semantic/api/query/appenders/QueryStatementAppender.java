package com.ontotext.semantic.api.query.appenders;

import java.io.Serializable;

import com.ontotext.semantic.api.common.Builder;

/**
 * Interface for appending a statement to a given builder
 * 
 * @param <Builder>
 *            a generic builder parameter
 * @author Svetlozar
 */
public interface QueryStatementAppender<B extends Builder> {


	/**
	 * Appends an undefined number of statement to the query
	 * 
	 * @param statements
	 *            the statements
	 * @return the builder
	 */
	public B appendStatement(Serializable... statements);
}
