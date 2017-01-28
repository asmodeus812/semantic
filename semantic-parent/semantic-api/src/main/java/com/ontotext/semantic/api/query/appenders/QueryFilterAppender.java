package com.ontotext.semantic.api.query.appenders;

import java.io.Serializable;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.enumeration.ArithmeticOperators;

/**
 * Interface for appending a given filter to a builder
 * 
 * @param <Builder>
 *            a generic builder parameter
 * @author Svetlozar
 */
public interface QueryFilterAppender<B extends Builder> {

	/**
	 * Appends a triplet filter to the query
	 * 
	 * @param variable
	 *            the variable
	 * @param operator
	 *            the operator
	 * @param value
	 *            the value
	 * @return the builder
	 */
	public B appendFilter(Serializable variable, ArithmeticOperators operator, Serializable value);

}
