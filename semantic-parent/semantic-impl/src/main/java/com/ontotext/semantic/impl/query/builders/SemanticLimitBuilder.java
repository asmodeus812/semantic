package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticQueryUtil.buildLimitBlock;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.findLimitAppendPosition;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.isSupportingLimitBlocks;

import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryGroupBuilder;
import com.ontotext.semantic.api.query.builders.QueryLimitBuilder;
import com.ontotext.semantic.api.query.compiler.QueryBlockCompiler;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;

/**
 * Semantic limit clause builder, builds a group by statement to a query
 * 
 * @author Svetlozar
 */
public class SemanticLimitBuilder implements QueryLimitBuilder {

	private QueryBlockCompiler compilator;
	private StringBuilder limitBlock = new StringBuilder(64);

	/**
	 * Initialize a limit clause builder with a given query block compiler
	 * 
	 * @param compilator
	 *            the query block compiler
	 */
	public SemanticLimitBuilder(QueryBlockCompiler compilator) {
		this.compilator = compilator;
	}

	@Override
	public QueryGroupBuilder appendLimit(int limit) {
		build();

		if (!isSupportingLimitBlocks(compilator.getType())) {
			throw new SemanticQueryException("Query does not support limit clause operator");
		}
		if (!isLimitEmpty()) {
			throw new SemanticQueryException("Query limit operator does not support multiple statements");
		}

		int pos = findLimitAppendPosition(limitBlock);
		limitBlock.insert(pos, limit);
		return new SemanticGroupBuilder(compilator);
	}

	@Override
	public QueryCompiler compile() {
		return compilator;
	}

	@Override
	public void build() {
		if (compilator.getLimitBlock() == null) {
			buildLimitBlock(limitBlock);
			compilator.setLimitBlock(limitBlock);
		} else {
			limitBlock = compilator.getLimitBlock();
		}
	}

	private boolean isLimitEmpty() {
		StringBuilder tmpLimit = new StringBuilder();
		buildLimitBlock(tmpLimit);
		return tmpLimit.length() == compilator.getLimitBlock().length();
	}
}
