package com.ontotext.semantic.api.query.appenders;

import java.io.Serializable;

import com.ontotext.semantic.api.common.Builder;

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
	public B appendGroup(Serializable value);
}
