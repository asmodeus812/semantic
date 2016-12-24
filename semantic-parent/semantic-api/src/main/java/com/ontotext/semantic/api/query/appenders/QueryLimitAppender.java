package com.ontotext.semantic.api.query.appenders;

import com.ontotext.semantic.api.common.Builder;

/**
 * Interface for appending a given limit to a builder
 * 
 * @param <Builder>
 *            a generic builder parameter
 * @author Svetlozar
 */
public interface QueryLimitAppender<B extends Builder> {

	/**
	 * Appends a query result limit to a builder
	 * 
	 * @param limit
	 *            the limit to be appended
	 * @return the builder
	 */
	public B appendLimit(int limit);
}
