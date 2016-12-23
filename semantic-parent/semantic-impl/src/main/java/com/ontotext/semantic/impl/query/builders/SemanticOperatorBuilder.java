package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticSearchUtil.SINGLE_SPACE;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.findFilterAppendPosition;

import com.ontotext.semantic.api.enumeration.LogicalOperators;
import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryBlockCompiler;
import com.ontotext.semantic.api.query.builders.QueryCompiler;
import com.ontotext.semantic.api.query.builders.QueryFilterBuilder;
import com.ontotext.semantic.api.query.builders.QueryOperatorBuilder;

/**
 * Semantic operator builder. Builds a filter to the given query
 * 
 * @author Svetlozar
 */
public class SemanticOperatorBuilder implements QueryOperatorBuilder {

	private QueryBlockCompiler compilator;

	/**
	 * Initialize the operator builder with a compiler
	 * 
	 * @param compilator
	 *            the compiler
	 */
	public SemanticOperatorBuilder(QueryBlockCompiler compilator) {
		this.compilator = compilator;
	}

	@Override
	public QueryCompiler getQueryCompiler() {
		return compilator;
	}

	@Override
	public QueryFilterBuilder appendLogicalOperator(LogicalOperators operator) {
		StringBuilder filterBlock = compilator.getFilterBlock();
		if (filterBlock == null) {
			throw new SemanticQueryException("No filter statements found to be connected operator");
		}
		int pos = findFilterAppendPosition(filterBlock);
		filterBlock.insert(pos, SINGLE_SPACE + operator);
		return new SemanticFilterBuilder(compilator);
	}

}