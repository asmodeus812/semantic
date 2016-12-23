package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Constructor;
import com.ontotext.semantic.api.structures.Triplet;

/**
 * Interface for query filter building
 * 
 * @author Svetlozar
 */
public interface QueryFilterBuilder extends Constructor {

	/**
	 * Append a filter to the given query
	 * 
	 * @param filter
	 *            the filter to be appended
	 * @return the query filter builder
	 */
	QueryOperatorBuilder appendFilter(Triplet filter);


}
