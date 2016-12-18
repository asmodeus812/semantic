package com.ontotext.semantic.core.api;

import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;

public interface SemanticTupleQuery extends SemanticQuery {

	public TupleQueryResult evaluate(RepositoryConnection connection);

}
