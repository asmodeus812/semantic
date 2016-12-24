package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticQueryUtil.buildFilterBlock;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.findFilterAppendPosition;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.isSupportingConditionBlocks;

import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryFilterBuilder;
import com.ontotext.semantic.api.query.builders.QueryOperatorBuilder;
import com.ontotext.semantic.api.query.compiler.QueryBlockCompiler;
import com.ontotext.semantic.api.structures.Triplet;

/**
 * Semantic filter builder. Builds filters to a semantic query
 * 
 * @author Svetlozar
 */
public class SemanticFilterBuilder implements QueryFilterBuilder {

	private QueryBlockCompiler compilator;
	private StringBuilder filterBlock = new StringBuilder(256);

	/**
	 * Initializes a filter builder for the given compiler
	 * 
	 * @param compilator
	 *            the compiler of the builder
	 */
	public SemanticFilterBuilder(QueryBlockCompiler compilator) {
		this.compilator = compilator;
		construct();
	}

	@Override
	public QueryOperatorBuilder appendFilter(Triplet filter) {
		if (!isSupportingConditionBlocks(compilator.getType())) {
			throw new SemanticQueryException("Specified query type does not support filter block");
		}
		int pos = findFilterAppendPosition(filterBlock);
		filterBlock.insert(pos, filter);
		return new SemanticOperatorBuilder(compilator);
	}

	@Override
	public void construct() {
		if (compilator.getFilterBlock() == null) {
			buildFilterBlock(filterBlock);
			compilator.setFilterBlock(filterBlock);
		} else {
			filterBlock = compilator.getFilterBlock();
		}
	}
}
