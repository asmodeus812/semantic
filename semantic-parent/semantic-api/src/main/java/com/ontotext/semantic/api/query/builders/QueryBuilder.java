package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.structures.Triplet;

/**
 * Interface used to build the base statement part of a given query
 * 
 * @author Svetlozar
 */
public interface QueryBuilder {

	/**
	 * Appends the statement of the query and return the statement builder
	 * 
	 * @param statement
	 *            the statement as a triplet
	 * @return the statement builder
	 */
	QueryStatementBuilder appendStatement(Triplet statement);
}
