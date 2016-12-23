package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.enumeration.LogicalOperators;

/**
 * Interface building an operator to a given query
 * 
 * @author Svetlozar
 */
public interface QueryOperatorBuilder {

	/**
	 * Append a logical operator after the last filter inserted
	 * 
	 * @param operator
	 *            the operator to be appended
	 * @return the query operator builder
	 */
	QueryFilterBuilder appendLogicalOperator(LogicalOperators operator);

	/**
	 * Get the query compilator proxy service
	 * 
	 * @return the query compilator proxy service
	 */
	QueryCompiler getQueryCompiler();
}
