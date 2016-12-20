package com.ontotext.semantic.impl.query;

import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.Update;
import org.openrdf.query.UpdateExecutionException;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import com.ontotext.semantic.api.exception.SemanticEvaluateException;
import com.ontotext.semantic.api.query.SemanticUpdateQuery;
import com.ontotext.semantic.core.common.SemanticQueryUtil;

/**
 * Represents a semantic modification query. Working with INSERT & DELETE statements
 * 
 * @author Svetlozar
 */
public class SemanticModifyQuery extends SemanticBaseQuery implements SemanticUpdateQuery {

	/**
	 * Initializes a semantic modification query
	 * 
	 * @param query
	 *            the query to be evaluated and executed
	 */
	public SemanticModifyQuery(String query) {
		super(query);
	}

	@Override
	public void evaluate(RepositoryConnection connection) {
		Update updateQuery;
		try {
			updateQuery = connection.prepareUpdate(QueryLanguage.SPARQL, getQuery());
			SemanticQueryUtil.prepareQueryParameters(updateQuery, getParameterMap());
			updateQuery.execute();
		} catch (RepositoryException | MalformedQueryException | UpdateExecutionException e) {
			throw new SemanticEvaluateException("Error during modification query evaluation " + e.getMessage());
		}
	}

}
