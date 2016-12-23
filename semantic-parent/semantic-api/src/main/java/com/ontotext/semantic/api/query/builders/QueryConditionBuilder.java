package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Constructor;
import com.ontotext.semantic.api.structures.Triplet;

/**
 * Interface for building a given conditions to a query
 * 
 * @author Svetlozar
 */
public interface QueryConditionBuilder extends Constructor {

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
	QueryOperatorBuilder appendFilter(Triplet filter);

	/**
	 * Get the query compilator proxy service
	 * 
	 * @return the query compilator proxy service
	 */
	QueryCompiler getQueryCompiler();
}
