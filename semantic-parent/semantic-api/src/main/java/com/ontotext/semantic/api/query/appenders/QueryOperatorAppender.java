package com.ontotext.semantic.api.query.appenders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.enumeration.LogicalOperators;

/**
 * Interface for appending query operation to a given builder
 * 
 * @param <Builder>
 *            a generic builder parameter
 * @author Svetlozar
 */
public interface QueryOperatorAppender<B extends Builder> {

	/**
	 * Append a logical operator after the last filter inserted
	 * 
	 * @param operator
	 *            the operator to be appended
	 * @return the builder
	 */
	public B appendLogicalOperator(LogicalOperators operator);
}
