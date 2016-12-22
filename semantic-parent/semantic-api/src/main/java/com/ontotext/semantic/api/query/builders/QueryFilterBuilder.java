package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.enumeration.LogicalOperators;

/**
 * Interface for query filter building
 * 
 * @author Svetlozar
 */
public interface QueryFilterBuilder {

	/**
	 * Append a logical operator after the last filter inserted
	 * 
	 * @param operator
	 *            the operator to be appended
	 * @return the query operator builder
	 */
	QueryOperatorBuilder appendLogicalOperator(LogicalOperators operator);

	/**
	 * Get the query compilator proxy service
	 * 
	 * @return the query compilator proxy service
	 */
	QueryCompilator getQueryCompilator();

}
