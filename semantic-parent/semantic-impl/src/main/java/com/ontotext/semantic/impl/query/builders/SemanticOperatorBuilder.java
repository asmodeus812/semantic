package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticQueryUtil.findFilterAppendPosition;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SINGLE_SPACE;

import com.ontotext.semantic.api.enumeration.LogicalOperators;
import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryFilterBuilder;
import com.ontotext.semantic.api.query.builders.QueryGroupBuilder;
import com.ontotext.semantic.api.query.builders.QueryLimitBuilder;
import com.ontotext.semantic.api.query.builders.QueryOperatorBuilder;
import com.ontotext.semantic.api.query.compiler.QueryBlockCompiler;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;
import com.ontotext.semantic.api.structures.Single;

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
	public QueryCompiler compile() {
		return compilator;
	}

	@Override
	public QueryFilterBuilder appendLogicalOperator(LogicalOperators operator) {
		StringBuilder filterBlock = compilator.getFilterBlock();
		if (filterBlock == null) {
			throw new SemanticQueryException("No filter statements found to be connected operator");
		}
		int pos = findFilterAppendPosition(filterBlock);
		String toInsert = SINGLE_SPACE + operator + SINGLE_SPACE;
		filterBlock.insert(pos, toInsert);
		return new SemanticFilterBuilder(compilator);
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
	public void build() {
	}
}
