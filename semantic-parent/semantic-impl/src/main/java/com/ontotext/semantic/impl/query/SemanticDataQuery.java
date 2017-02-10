package com.ontotext.semantic.impl.query;

import org.openrdf.model.Value;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.Update;
import org.openrdf.query.UpdateExecutionException;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import com.ontotext.semantic.api.exception.SemanticEvaluateException;
import com.ontotext.semantic.api.query.SemanticUpdateQuery;
import com.ontotext.semantic.core.common.SemanticNamespaceUtil;
import com.ontotext.semantic.core.common.SemanticSparqlUtil;

/**
 * Represents a semantic modification query supporting INSERT DATA & DELETE DATA uses common query syntax ?variable and
 * binds to that variable any given parameter value
 * 
 * @author Svetlozar
 */
public class SemanticDataQuery extends SemanticBaseQuery implements SemanticUpdateQuery {

	/**
	 * Initializes a semantic modification query
	 * 
	 * @param query
	 *            the query
	 */
	public SemanticDataQuery(String query) {
		super(query);
	}

	@Override
	public void bind(String parameter, Value binding) {
		setQuery(getQuery().replaceAll("\\" + SemanticSparqlUtil.VARSYMBOL + parameter,
				SemanticNamespaceUtil.convertValueForQuery(binding)));
		super.bind(parameter, binding);
	}

	@Override
	public void unbind(String parameter) {
		Value binding = getParameterMap().get(parameter).getValue();
		setQuery(getQuery().replaceAll(SemanticNamespaceUtil.convertValueForQuery(binding),
				"\\" + SemanticSparqlUtil.VARSYMBOL + parameter));
		super.unbind(parameter);
	}

	@Override
	public Void evaluate(RepositoryConnection connection) {
		Update updateQuery;
		try {
			updateQuery = connection.prepareUpdate(QueryLanguage.SPARQL, getQuery());
			updateQuery.execute();
		} catch (RepositoryException | MalformedQueryException | UpdateExecutionException e) {
			throw new SemanticEvaluateException(
					"Error during evaluating semantic modification query " + e.getMessage());
		}
		return null;
	}
}
