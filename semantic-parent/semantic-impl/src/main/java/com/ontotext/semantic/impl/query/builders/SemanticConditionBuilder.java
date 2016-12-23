package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticSearchUtil.DOT;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.buildWhereBlock;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.findWhereAppendPosition;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.isSupportingConditionBlocks;

import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryBlockCompiler;
import com.ontotext.semantic.api.query.builders.QueryCompiler;
import com.ontotext.semantic.api.query.builders.QueryConditionBuilder;
import com.ontotext.semantic.api.query.builders.QueryFilterBuilder;
import com.ontotext.semantic.api.query.builders.QueryOperatorBuilder;
import com.ontotext.semantic.api.structures.Triplet;

/**
 * Semantic condition builder. Builds various conditions to a semantic query such as - filter and where blocks
 * 
 * @author Svetlozar
 */
public class SemanticConditionBuilder implements QueryConditionBuilder {

	private QueryBlockCompiler compilator;
	private StringBuilder whereBlock = new StringBuilder(256);

	/**
	 * Initialize a semantic condition builder
	 * 
	 * @param compilator
	 *            the compilator for this builder
	 */
	public SemanticConditionBuilder(QueryBlockCompiler compilator) {
		this.compilator = compilator;
		construct();
	}

	@Override
	public QueryOperatorBuilder appendFilter(Triplet filter) {
		QueryFilterBuilder filterBuilder = new SemanticFilterBuilder(compilator);
		filterBuilder.appendFilter(filter);
		return new SemanticOperatorBuilder(compilator);
	}

	@Override
	public QueryConditionBuilder appendCondition(Triplet condition) {
		if (!isSupportingConditionBlocks(compilator.getType())) {
			throw new SemanticQueryException("Specified query type does not support condition blocks");
		}
		int pos = findWhereAppendPosition(whereBlock);
		whereBlock.insert(pos, condition + DOT);
		return this;
	}

	@Override
	public void construct() {
		if (compilator.getWhereBlock() == null) {
			buildWhereBlock(whereBlock);
			compilator.setWhereBlock(whereBlock);
		} else {
			whereBlock = compilator.getWhereBlock();
		}
	}

	@Override
	public QueryCompiler getQueryCompiler() {
		return compilator;
	}
}
