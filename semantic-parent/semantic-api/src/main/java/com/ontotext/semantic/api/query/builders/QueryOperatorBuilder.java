package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.structures.Triplet;

/**
 * Interface building an operator to a given query
 * 
 * @author Svetlozar
 */
public interface QueryOperatorBuilder {

	/**
	 * Appends a filter to the query
	 * 
	 * @param filter
	 *            the filter to be appended
	 * @return the query filter builder
	 */
	QueryFilterBuilder appendFilter(Triplet filter);
}
