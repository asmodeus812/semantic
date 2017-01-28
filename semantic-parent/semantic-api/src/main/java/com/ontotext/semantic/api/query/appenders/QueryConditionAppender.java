package com.ontotext.semantic.api.query.appenders;

import java.io.Serializable;

import com.ontotext.semantic.api.common.Builder;

/**
 * Interface for appending a given condition triplet to a given builder
 * 
 * @param <B>
 *            a generic builder parameter
 * @author Svetlozar
 */
public interface QueryConditionAppender<B extends Builder> {

	/**
	 * Appends a triplet condition to the query
	 * 
	 * @param subject
	 *            the subject
	 * @param predicate
	 *            the predicate
	 * @param object
	 *            the object
	 * @return the builder
	 */
	public B appendCondition(Serializable subject, Serializable predicate, Serializable object);
}
