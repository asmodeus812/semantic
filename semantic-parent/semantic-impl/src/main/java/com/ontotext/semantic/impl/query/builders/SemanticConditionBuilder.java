package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticQueryUtil.buildWhereBlock;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.findWhereAppendPosition;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.isSupportingConditionBlocks;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.DOT;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SINGLE_SPACE;

import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryConditionBuilder;
import com.ontotext.semantic.api.query.builders.QueryFilterBuilder;
import com.ontotext.semantic.api.query.builders.QueryGroupBuilder;
import com.ontotext.semantic.api.query.builders.QueryLimitBuilder;
import com.ontotext.semantic.api.query.builders.QueryOperatorBuilder;
import com.ontotext.semantic.api.query.compiler.QueryBlockCompiler;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;
import com.ontotext.semantic.api.structures.Single;
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
		build();
	}

	@Override
	public QueryOperatorBuilder appendFilter(Triplet filter) {
		QueryFilterBuilder filterBuilder = new SemanticFilterBuilder(compilator);
		filterBuilder.appendFilter(filter);
		return new SemanticOperatorBuilder(compilator);
	}

	@Override
	public QueryLimitBuilder appendGroup(Single value) {
		QueryGroupBuilder groupBuilder = new SemanticGroupBuilder(compilator);
		groupBuilder.appendGroup(value);
		return new SemanticLimitBuilder(compilator);
	}

	@Override
	public QueryGroupBuilder appendLimit(int limit) {
		QueryLimitBuilder limitBuilder = new SemanticLimitBuilder(compilator);
		limitBuilder.appendLimit(limit);
		return new SemanticGroupBuilder(compilator);
	}

	@Override
	public QueryConditionBuilder appendCondition(Triplet condition) {
		if (!isSupportingConditionBlocks(compilator.getType())) {
			throw new SemanticQueryException("Specified query type does not support condition blocks");
		}
		String appendToCondition = DOT + SINGLE_SPACE;
		int pos = findWhereAppendPosition(whereBlock);
		whereBlock.insert(pos, condition + appendToCondition);
		return this;
	}

	@Override
	public void build() {
		if (compilator.getWhereBlock() == null) {
			buildWhereBlock(whereBlock);
			compilator.setWhereBlock(whereBlock);
		} else {
			whereBlock = compilator.getWhereBlock();
		}
	}

	@Override
	public QueryCompiler compile() {
		return compilator;
	}

}
