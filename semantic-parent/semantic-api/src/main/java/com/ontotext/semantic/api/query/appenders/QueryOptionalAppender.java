package com.ontotext.semantic.api.query.appenders;

import java.io.Serializable;

import com.ontotext.semantic.api.common.Builder;

/**
 * Interface for appending a optional statement to a given builder
 * 
 * @param <Builder>
 *            a generic builder parameter
 * @author Svetlozar
 */
public interface QueryOptionalAppender<B extends Builder> {

	/**
	 * Appends a triplet to the optional block
	 * 
	 * @param subject
	 *            the subject
	 * @param predicate
	 *            the predicate
	 * @param object
	 *            the object
	 * @return the builder
	 */
	public B appendOptional(Serializable subject, Serializable predicate, Serializable object);
}
