package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticSearchUtil.SINGLE_SPACE;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.findFilterAppendPosition;

import com.ontotext.semantic.api.enumeration.LogicalOperators;
import com.ontotext.semantic.api.query.builders.QueryBlockCompilator;
import com.ontotext.semantic.api.query.builders.QueryCompilator;
import com.ontotext.semantic.api.query.builders.QueryFilterBuilder;
import com.ontotext.semantic.api.query.builders.QueryOperatorBuilder;

/**
 * Semantic filter builder. Builds filters to a semantic query
 * 
 * @author Svetlozar
 */
public class SemanticFilterBuilder implements QueryFilterBuilder {

	private QueryBlockCompilator compilator;
	private StringBuilder filterBlock;

	/**
	 * Initialize a semantic filter builder for a given compiler and filter block
	 * 
	 * @param compilator
	 *            the compiler
	 * @param filterBlock
	 *            the filter block
	 */
	public SemanticFilterBuilder(QueryBlockCompilator compilator, StringBuilder filterBlock) {
		this.compilator = compilator;
		this.filterBlock = filterBlock;
	}

	@Override
	public QueryOperatorBuilder appendLogicalOperator(LogicalOperators operator) {
		int pos = findFilterAppendPosition(filterBlock);
		filterBlock.insert(pos, SINGLE_SPACE + operator);
		return new SemanticOperatorBuilder(compilator, filterBlock);
	}

	@Override
	public QueryCompilator getQueryCompilator() {
		return compilator;
	}

}
