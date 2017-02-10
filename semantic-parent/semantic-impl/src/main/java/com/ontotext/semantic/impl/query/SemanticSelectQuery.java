package com.ontotext.semantic.impl.query;

import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import com.ontotext.semantic.api.exception.SemanticEvaluateException;
import com.ontotext.semantic.api.query.SemanticTupleQuery;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;
import com.ontotext.semantic.core.common.SemanticQueryUtil;

/**
 * Represents a semantic SPARQL select query
 * 
 * @author Svetlozar
 */
public class SemanticSelectQuery extends SemanticBaseQuery implements SemanticTupleQuery {

	/**
	 * Initializes a semantic select or a tuple query
	 * 
	 * @param queryCompiler
	 *            the query compiler
	 */
	public SemanticSelectQuery(QueryCompiler queryCompiler) {
		super(queryCompiler.longFormatQuery());
	}

	/**
	 * Initializes a semantic select or a tuple query
	 * 
	 * @param query
	 *            the query
	 */
	public SemanticSelectQuery(String query) {
		super(query);
	}

	@Override
	public TupleQueryResult evaluate(RepositoryConnection connection) {
		TupleQuery tupleQuery;
		try {
			tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, getQuery());
			SemanticQueryUtil.prepareQueryParameters(tupleQuery, getParameterMap());
			return tupleQuery.evaluate();
		} catch (RepositoryException | MalformedQueryException | QueryEvaluationException e) {
			throw new SemanticEvaluateException("Error during tuple query evaluation " + e.getMessage());
		}
	}
}
