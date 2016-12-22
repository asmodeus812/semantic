package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticSearchUtil.DOT;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.buildWhereBlock;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.findWhereAppendPosition;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.isSupportingConditionBlocks;

import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryBlockCompilator;
import com.ontotext.semantic.api.query.builders.QueryConditionBuilder;
import com.ontotext.semantic.api.query.builders.QueryFilterBuilder;
import com.ontotext.semantic.api.structures.Triplet;

/**
 * Semantic condition builder. Builds various conditions to a semantic query such as - filter and where blocks
 * 
 * @author Svetlozar
 */
public class SemanticConditionBuilder implements QueryConditionBuilder {

	private QueryBlockCompilator compilator;
	private StringBuilder whereBlock = new StringBuilder(256);

	/**
	 * Initialize a semantic condition builder
	 * 
	 * @param compilator
	 *            the compilator for this builder
	 */
	public SemanticConditionBuilder(QueryBlockCompilator compilator) {
		this.compilator = compilator;
		buildWhereBlock(whereBlock);
	}

	@Override
	public QueryFilterBuilder appendFilter(Triplet filter) {
		compilator.setWhereBlock(whereBlock);
		QueryFilterBuilder filterBuilder = new SemanticOperatorBuilder(compilator).appendFilter(filter);
		return filterBuilder;
	}

	@Override
	public QueryConditionBuilder appendCondition(Triplet condition) {
		if (isSupportingConditionBlocks(compilator.getType())) {
			throw new SemanticQueryException("Specified query type does not support condition blocks");
		}
		int pos = findWhereAppendPosition(whereBlock);
		whereBlock.insert(pos, condition + DOT);
		return this;
	}
}
