package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticSearchUtil.buildFilterBlock;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.findFilterAppendPosition;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.isSupportingConditionBlocks;

import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryBlockCompilator;
import com.ontotext.semantic.api.query.builders.QueryFilterBuilder;
import com.ontotext.semantic.api.query.builders.QueryOperatorBuilder;
import com.ontotext.semantic.api.structures.Triplet;

/**
 * Semantic operator builder. Builds a filter to the given query
 * 
 * @author Svetlozar
 */
public class SemanticOperatorBuilder implements QueryOperatorBuilder {

	private QueryBlockCompilator compilator;
	private StringBuilder filterBlock = new StringBuilder(256);

	/**
	 * Initialize the operator builder with a compiler
	 * 
	 * @param compilator
	 *            the compiler
	 */
	public SemanticOperatorBuilder(QueryBlockCompilator compilator) {
		this.compilator = compilator;
		buildFilterBlock(filterBlock);
	}

	public SemanticOperatorBuilder(QueryBlockCompilator compilator, StringBuilder filterBlock) {
		this.compilator = compilator;
		this.filterBlock = filterBlock;
	}

	@Override
	public QueryFilterBuilder appendFilter(Triplet filter) {
		if (isSupportingConditionBlocks(compilator.getType())) {
			throw new SemanticQueryException("Specified query type does not support filter block");
		}
		int pos = findFilterAppendPosition(filterBlock);
		filterBlock.insert(pos, filter);
		compilator.setFilterBlock(filterBlock);
		return new SemanticFilterBuilder(compilator, filterBlock);
	}

}
