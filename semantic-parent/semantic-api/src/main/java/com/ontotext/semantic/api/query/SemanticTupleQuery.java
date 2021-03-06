package com.ontotext.semantic.api.query;

import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;

/**
 * Represents a semantic tuple query. Usually represented as a SELECT statement of triplets
 * 
 * @author Svetlozar
 */
public interface SemanticTupleQuery extends SemanticQuery, SemanticEvaluator<TupleQueryResult, RepositoryConnection> {

	@Override
	public TupleQueryResult evaluate(RepositoryConnection connection);

}
