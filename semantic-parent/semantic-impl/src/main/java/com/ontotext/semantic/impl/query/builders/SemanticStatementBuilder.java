package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticSearchUtil.DOT;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.buildStatementBlock;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.findStatementAppendPosition;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.query.builders.QueryBlockCompilator;
import com.ontotext.semantic.api.query.builders.QueryConditionBuilder;
import com.ontotext.semantic.api.query.builders.QueryStatementBuilder;
import com.ontotext.semantic.api.structures.Triplet;

/**
 * Semantic statement builder. Builds a base statement or a condition to a query
 * 
 * @author Svetlozar
 */
public class SemanticStatementBuilder implements QueryStatementBuilder {

	private QueryBlockCompilator compilator;
	private StringBuilder statementBlock = new StringBuilder(256);

	/**
	 * Initialize a semantic statement builder
	 * 
	 * @param compilator
	 *            the compiler
	 */
	public SemanticStatementBuilder(QueryBlockCompilator compilator) {
		this.compilator = compilator;
		buildStatementBlock(statementBlock, this.compilator.getType());
	}

	@Override
	public QueryConditionBuilder appendCondition(Triplet condition) {
		compilator.setStatementBlock(statementBlock);
		QueryConditionBuilder condBuilder = new SemanticConditionBuilder(compilator);
		condBuilder.appendCondition(condition);
		return condBuilder;
	}

	@Override
	public QueryStatementBuilder appendStatement(Triplet statement) {
		SemanticQueryType type = compilator.getType();
		int pos = findStatementAppendPosition(statementBlock, type);
		String toInsert = (type == SemanticQueryType.SELECT) ? statement.toString() : statement + DOT;
		statementBlock.insert(pos, toInsert);
		return this;
	}

}
