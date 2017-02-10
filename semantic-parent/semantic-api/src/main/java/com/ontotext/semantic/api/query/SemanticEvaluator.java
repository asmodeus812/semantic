package com.ontotext.semantic.api.query;

import org.openrdf.repository.RepositoryConnection;

/**
 * Interface for evaluating semantic queries
 * 
 * @param <R>
 *            the return type & data of the query after it has been evaluated
 * @param <C>
 *            the RepositoryConnection for which to evaluate the query
 * @author Svetlozar
 */
public interface SemanticEvaluator<R extends Object, C extends RepositoryConnection> {

	/**
	 * Evaluates a semantic tuple query
	 * 
	 * @param connection
	 *            the connection
	 * @return the query result
	 */
	public R evaluate(C connection);
}
