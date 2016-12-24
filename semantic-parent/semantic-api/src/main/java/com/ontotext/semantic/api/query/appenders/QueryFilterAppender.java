package com.ontotext.semantic.api.query.appenders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.structures.Triplet;

/**
 * Interface for appending a given filter to a builder
 * 
 * @param <Builder>
 *            a generic builder parameter
 * @author Svetlozar
 */
public interface QueryFilterAppender<B extends Builder> {

	/**
	 * Append a filter to the given query
	 * 
	 * @param filter
	 *            the filter to be appended
	 * @return the builder
	 */
	B appendFilter(Triplet filter);

}
