package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticQueryUtil.buildFilterBlock;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.findFilterAppendPosition;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.isSupportingConditionBlocks;

import java.io.Serializable;

import com.ontotext.semantic.api.enumeration.ArithmeticOperators;
import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryFilterBuilder;
import com.ontotext.semantic.api.query.builders.QueryOperatorBuilder;
import com.ontotext.semantic.api.query.compiler.QueryBlockCompiler;
import com.ontotext.semantic.api.structures.Triplet;
import com.ontotext.semantic.impl.structures.SemanticTriplet;

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
		build();
	}

	@Override
	public QueryOperatorBuilder appendFilter(Serializable variable, ArithmeticOperators operator, Serializable value) {
		if (!isSupportingConditionBlocks(compilator.getType())) {
			throw new SemanticQueryException("Specified query type does not support filter block");
		}

		int pos = findFilterAppendPosition(filterBlock);
		Triplet filter = new SemanticTriplet(variable, operator, value);
		filterBlock.insert(pos, filter);
		return new SemanticOperatorBuilder(compilator);
	}

	@Override
	public void build() {
		if (compilator.getFilterBlock() == null) {
			buildFilterBlock(filterBlock);
			compilator.setFilterBlock(filterBlock);
		} else {
			filterBlock = compilator.getFilterBlock();
		}
	}
}
