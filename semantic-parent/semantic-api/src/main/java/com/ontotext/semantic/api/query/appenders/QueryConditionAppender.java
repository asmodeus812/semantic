package com.ontotext.semantic.api.query.appenders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.structures.Triplet;

/**
 * Interface for appending a given condition triplet to a given builder
 * 
 * @param <B>
 *            a generic builder parameter
 * @author Svetlozar
 */
public interface QueryConditionAppender<B extends Builder> {

	/**
	 * Appends a condition to the given query
	 * 
	 * @param condition
	 *            the condition to be appended
	 * @return the builder
	 */
	B appendCondition(Triplet condition);
}
