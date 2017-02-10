package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticQueryUtil.buildOptionalBlock;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.findOptionalAppendPosition;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.isSupportingConditionBlocks;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.DOT;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SINGLE_SPACE;

import java.io.Serializable;

import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryConditionBuilder;
import com.ontotext.semantic.api.query.builders.QueryOptionalBuilder;
import com.ontotext.semantic.api.query.compiler.QueryBlockCompiler;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;
import com.ontotext.semantic.api.structures.Triplet;
import com.ontotext.semantic.impl.structures.SemanticTriplet;

public class SemanticOptionalBuilder implements QueryOptionalBuilder {

	private QueryBlockCompiler compilator;
	private StringBuilder optionalBlock = new StringBuilder(64);

	public SemanticOptionalBuilder(QueryBlockCompiler compiler) {
		this.compilator = compiler;
		build();
	}

	@Override
	public void build() {
		if (compilator.getOptionalBlock() == null) {
			buildOptionalBlock(optionalBlock);
			compilator.setOptionalBlock(optionalBlock);
		} else {
			optionalBlock = compilator.getOptionalBlock();
		}
	}

	@Override
	public QueryOptionalBuilder appendOptional(Serializable subject, Serializable predicate, Serializable object) {
		if (!isSupportingConditionBlocks(compilator.getType())) {
			throw new SemanticQueryException("Specified query type does not support optional blocks");
		}

		Triplet triplet = new SemanticTriplet(subject, predicate, object);
		int pos = findOptionalAppendPosition(optionalBlock);
		optionalBlock.insert(pos, triplet + DOT + SINGLE_SPACE);
		return this;
	}

	@Override
	public QueryConditionBuilder appendCondition(Serializable subject, Serializable predicate, Serializable object) {
		QueryConditionBuilder condBuilder = new SemanticConditionBuilder(compilator);
		condBuilder.appendCondition(subject, predicate, object);
		return condBuilder;
	}

	@Override
	public QueryCompiler compile() {
		return compilator;
	}

}
