package com.ontotext.semantic.api.query;

import java.util.List;

import org.openrdf.repository.RepositoryConnection;

import com.ontotext.semantic.api.instance.Instance;

/**
 * Parses a given generic semantic query as a list of instances
 * 
 * @param <T>
 *            the generic semantic query extending the semantic query
 * @author Svetlozar
 */
public interface SemanticQueryParser<T extends SemanticQuery> {

	/**
	 * Parse a semantic query as a list of instances
	 * 
	 * @param connection
	 *            the connection to the semantic data base
	 * @param query
	 *            the query to be parsed and evaluated
	 * @return the parsed collection of instances
	 */
	public List<Instance> parseQuery(RepositoryConnection connection, T query);
}
