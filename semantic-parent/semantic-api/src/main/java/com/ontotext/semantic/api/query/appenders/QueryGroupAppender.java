package com.ontotext.semantic.api.query.appenders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.structures.Single;

/**
 * Interface for appending a given group to a builder
 * 
 * @param <Builder>
 *            a generic builder parameter
 * @author Svetlozar
 */
public interface QueryGroupAppender<B extends Builder> {

	/**
	 * Appends a given group by clause to the builder
	 * 
	 * @param value
	 *            the group by to be appended
	 * @return the builder
	 */
	public B appendGroup(Single value);
}
