package com.ontotext.semantic.api.query;

import org.openrdf.repository.RepositoryConnection;

/**
 * Represents a semantic update & modification query. Usually INSERT / DELETE query statements
 * 
 * @author Svetlozar
 */
public interface SemanticUpdateQuery extends SemanticQuery, SemanticEvaluator<Void, RepositoryConnection> {

	@Override
	public Void evaluate(RepositoryConnection connection);
}
