package com.ontotext.semantic.api.query.appenders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.structures.Triplet;

/**
 * Interface for appending a statement to a given builder
 * 
 * @param <Builder>
 *            a generic builder parameter
 * @author Svetlozar
 */
public interface QueryStatementAppender<B extends Builder> {

	/**
	 * Append a statement to the given query
	 * 
	 * @param statement
	 *            the statement to be appended
	 * @return the builder
	 */
	B appendStatement(Triplet statement);
}
