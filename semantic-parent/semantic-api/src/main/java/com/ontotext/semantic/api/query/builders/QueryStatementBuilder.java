package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.structures.Triplet;

/**
 * Interface for query statement and condition building
 * 
 * @author Svetlozar
 */
public interface QueryStatementBuilder {

	/**
	 * Append a statement to the given query
	 * 
	 * @param statement
	 *            the statement to be appended
	 * @return the statement query builder
	 */
	QueryStatementBuilder appendStatement(Triplet statement);

	/**
	 * Appends a condition to the given query
	 * 
	 * @param condition
	 *            the condition to be appended
	 * @return the condition query builder
	 */
	QueryConditionBuilder appendCondition(Triplet condition);
}
