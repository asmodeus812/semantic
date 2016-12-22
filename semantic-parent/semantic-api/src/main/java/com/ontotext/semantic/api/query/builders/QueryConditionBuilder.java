package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.structures.Triplet;

/**
 * Interface for building a given conditions to a query
 * 
 * @author Svetlozar
 */
public interface QueryConditionBuilder {

	/**
	 * Append a condition to the query
	 * 
	 * @param condition
	 *            the condition to be appended
	 * @return the query condition builder
	 */
	QueryConditionBuilder appendCondition(Triplet condition);

	/**
	 * Append a filter to the given query
	 * 
	 * @param filter
	 *            the filter to be appended
	 * @return the query filter builder
	 */
	QueryFilterBuilder appendFilter(Triplet filter);
}
