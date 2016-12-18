package com.ontotext.semantic.core.api;

import org.openrdf.repository.RepositoryConnection;

public interface SemanticUpdateQuery extends SemanticQuery {

	public void evaluate(RepositoryConnection connection);
}
