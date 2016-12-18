package com.ontotext.semantic.api.query;

import org.openrdf.repository.RepositoryConnection;

/**
 * Represents a semantic update & modification query. Usually INSERT / DELETE query statements
 * 
 * @author Svetlozar
 */
public interface SemanticUpdateQuery extends SemanticQuery {

	/**
	 * Evaluates the semantic query. No result is expected if successful
	 * 
	 * @param connection
	 *            the connection
	 */
	public void evaluate(RepositoryConnection connection);
}
