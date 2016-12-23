package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticQueryUtil.PARAMETER_PATTERN;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.DOT;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.buildStatementBlock;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.findStatementAppendPosition;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.isSupportingConditionBlocks;

import java.util.regex.Matcher;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.exception.SemanticQueryException;
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
		construct();
	}

	@Override
	public QueryConditionBuilder appendCondition(Triplet condition) {
		QueryConditionBuilder condBuilder = new SemanticConditionBuilder(compilator);
		condBuilder.appendCondition(condition);
		return condBuilder;
	}

	@Override
	public QueryStatementBuilder appendStatement(Triplet statement) {
		SemanticQueryType type = compilator.getType();
		if (isSupportingConditionBlocks(type) && countStatements(3) >= 1) {
			throw new SemanticQueryException("Query type does not support multiple triplet statements");
		}

		int pos = findStatementAppendPosition(statementBlock, type);
		String toInsert = (type == SemanticQueryType.SELECT) ? statement.toString() : statement + DOT;
		statementBlock.insert(pos, toInsert);
		// Always append the first statement as a condition
		if (isSupportingConditionBlocks(type)) {
			appendCondition(statement);
		}
		return this;
	}

	@Override
	public void construct() {
		if (compilator.getStatementBlock() == null) {
			buildStatementBlock(statementBlock, compilator.getType());
			compilator.setStatementBlock(statementBlock);
		} else {
			statementBlock = compilator.getStatementBlock();
		}
	}

	/**
	 * Counts the number of triplets in the statement block
	 * 
	 * @return the number of triplets
	 */
	private int countStatements(int statementSize) {
		Matcher paramterMatcher = PARAMETER_PATTERN.matcher(statementBlock);

		int matchCount = 0;
		while (paramterMatcher.find()) {
			matchCount++;
		}
		return matchCount / statementSize;
	}

}
