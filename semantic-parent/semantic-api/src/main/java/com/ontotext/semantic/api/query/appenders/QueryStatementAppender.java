package com.ontotext.semantic.api.query.appenders;

import java.io.Serializable;

import com.ontotext.semantic.api.common.Builder;

/**
 * Interface for appending a statement to a given builder
 * 
 * @param <Builder>
 *            a generic builder parameter
 * @author Svetlozar
 */
public interface QueryStatementAppender<B extends Builder> {

	/**
	 * Appends a single statement to the query
	 * 
	 * @param subject
	 *            the subject statement
	 * @return the builder
	 */
	public B appendStatement(Serializable subject);

	/**
	 * Appends a pair statement to the query
	 * 
	 * @param subject
	 *            the subject
	 * @param predicate
	 *            the predicate
	 * @return the builder
	 */
	public B appendStatement(Serializable subject, Serializable predicate);

	/**
	 * Appends a triplet statement to the query
	 * 
	 * @param subject
	 *            the subject
	 * @param predicate
	 *            the predicate
	 * @param object
	 *            the object
	 * @return the builder
	 */
	public B appendStatement(Serializable subject, Serializable predicate, Serializable object);

}
