package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticQueryUtil.buildStatementBlock;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.findStatementAppendPosition;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.isSupportingConditionBlocks;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.DOT;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.EMPTY_STRING;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SINGLE_SPACE;

import java.io.Serializable;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryConditionBuilder;
import com.ontotext.semantic.api.query.builders.QueryGroupBuilder;
import com.ontotext.semantic.api.query.builders.QueryLimitBuilder;
import com.ontotext.semantic.api.query.builders.QueryStatementBuilder;
import com.ontotext.semantic.api.query.compiler.QueryBlockCompiler;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;
import com.ontotext.semantic.impl.structures.SemanticPair;
import com.ontotext.semantic.impl.structures.SemanticSingle;
import com.ontotext.semantic.impl.structures.SemanticTriplet;

/**
 * Semantic statement builder. Builds a base statement or a condition to a query
 * 
 * @author Svetlozar
 */
public class SemanticStatementBuilder implements QueryStatementBuilder {

	private int appendCounter = 0;
	private QueryBlockCompiler compilator;
	private StringBuilder statementBlock = new StringBuilder(256);

	/**
	 * Initialize a semantic statement builder
	 * 
	 * @param compilator
	 *            the compiler
	 */
	public SemanticStatementBuilder(QueryBlockCompiler compilator) {
		this.compilator = compilator;
		build();
	}

	@Override
	public QueryConditionBuilder appendCondition(Serializable subject, Serializable predicate, Serializable object) {
		QueryConditionBuilder condBuilder = new SemanticConditionBuilder(compilator);
		condBuilder.appendCondition(subject, predicate, object);
		return condBuilder;
	}

	@Override
	public QueryLimitBuilder appendGroup(Serializable value) {
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
	public QueryStatementBuilder appendStatement(Serializable subject) {
		return append(new SemanticSingle(subject));
	}

	@Override
	public QueryStatementBuilder appendStatement(Serializable subject, Serializable predicate) {
		return append(new SemanticPair(subject, predicate));
	}

	@Override
	public QueryStatementBuilder appendStatement(Serializable subject, Serializable predicate, Serializable object) {
		if (isSupportingConditionBlocks(compilator.getType())) {
			appendCondition(subject, predicate, object);
		}
		return append(new SemanticTriplet(subject, predicate, object));
	}

	@Override
	public QueryCompiler compile() {
		return compilator;
	}

	@Override
	public void build() {
		if (compilator.getStatementBlock() == null) {
			buildStatementBlock(statementBlock, compilator.getType());
			compilator.setStatementBlock(statementBlock);
		} else {
			statementBlock = compilator.getStatementBlock();
		}
	}

	/**
	 * Appends a statement to the query
	 * 
	 * @param statement
	 *            the statement to be appended
	 * @return the statement builder
	 */
	private QueryStatementBuilder append(Serializable statement) {
		SemanticQueryType type = compilator.getType();
		if (isSupportingConditionBlocks(type) && appendCounter >= 1) {
			throw new SemanticQueryException("Query does not support more than a single statement");
		}

		++appendCounter;
		int pos = findStatementAppendPosition(statementBlock, type);
		String separator = (!isSupportingConditionBlocks(type)) ? DOT : EMPTY_STRING;
		statementBlock.insert(pos, statement + separator + SINGLE_SPACE);
		return this;
	}
}
