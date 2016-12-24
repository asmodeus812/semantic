package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticQueryUtil.PARAMETER_PATTERN;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.buildStatementBlock;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.findStatementAppendPosition;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.isSupportingConditionBlocks;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.DOT;

import java.util.regex.Matcher;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryConditionBuilder;
import com.ontotext.semantic.api.query.builders.QueryGroupBuilder;
import com.ontotext.semantic.api.query.builders.QueryLimitBuilder;
import com.ontotext.semantic.api.query.builders.QueryStatementBuilder;
import com.ontotext.semantic.api.query.compiler.QueryBlockCompiler;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;
import com.ontotext.semantic.api.structures.Single;
import com.ontotext.semantic.api.structures.Triplet;

/**
 * Semantic statement builder. Builds a base statement or a condition to a query
 * 
 * @author Svetlozar
 */
public class SemanticStatementBuilder implements QueryStatementBuilder {

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
	public QueryConditionBuilder appendCondition(Triplet condition) {
		QueryConditionBuilder condBuilder = new SemanticConditionBuilder(compilator);
		condBuilder.appendCondition(condition);
		return condBuilder;
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
	public <T extends Single> QueryStatementBuilder appendStatement(T statement) {
		SemanticQueryType type = compilator.getType();
		if (isSupportingConditionBlocks(type) && countStatementParameters(3) >= 1) {
			throw new SemanticQueryException("Query type does not support multiple triplet statements");
		}

		int pos = findStatementAppendPosition(statementBlock, type);
		String toInsert = (type == SemanticQueryType.SELECT) ? statement.toString() : statement + DOT;
		statementBlock.insert(pos, toInsert);

		// Always append the first statement as a condition if it is a triplet
		if (isSupportingConditionBlocks(type) && statement instanceof Triplet) {
			appendCondition((Triplet) statement);
		}
		return this;
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
	 * Counts the number of triplets in the statement block
	 * 
	 * @return the number of triplets
	 */
	private int countStatementParameters(int statementSize) {
		Matcher paramterMatcher = PARAMETER_PATTERN.matcher(statementBlock);

		int matchCount = 0;
		while (paramterMatcher.find()) {
			matchCount++;
		}
		return matchCount / statementSize;
	}

}
