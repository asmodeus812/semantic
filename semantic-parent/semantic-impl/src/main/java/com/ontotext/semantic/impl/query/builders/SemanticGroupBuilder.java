package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticQueryUtil.buildGroupBlock;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.findGroupAppendPosition;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.isSupportingGroupBlocks;

import java.io.Serializable;

import com.ontotext.semantic.api.exception.SemanticQueryException;
import com.ontotext.semantic.api.query.builders.QueryGroupBuilder;
import com.ontotext.semantic.api.query.builders.QueryLimitBuilder;
import com.ontotext.semantic.api.query.compiler.QueryBlockCompiler;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;

/**
 * Semantic group by builder, builds a group by statement to a query
 * 
 * @author Svetlozar
 */
public class SemanticGroupBuilder implements QueryGroupBuilder {

	private QueryBlockCompiler compilator;
	private StringBuilder groupBlock = new StringBuilder(128);

	/**
	 * Initialize a group by builder with a given query block compiler
	 * 
	 * @param compilator
	 *            the query block compiler
	 */
	public SemanticGroupBuilder(QueryBlockCompiler compilator) {
		this.compilator = compilator;
	}

	@Override
	public QueryLimitBuilder appendGroup(Serializable value) {
		// TODO: find a better solution than lazy building
		build();

		if (!isSupportingGroupBlocks(compilator.getType())) {
			throw new SemanticQueryException("Query does not support group by clause");
		}
		if (!isGroupEmpty()) {
			throw new SemanticQueryException("Query group operator does not support multiple groups");
		}

		int pos = findGroupAppendPosition(groupBlock);
		groupBlock.insert(pos, value);
		return new SemanticLimitBuilder(compilator);
	}

	@Override
	public QueryCompiler compile() {
		return compilator;
	}

	@Override
	public void build() {
		if (compilator.getGroupBlock() == null) {
			buildGroupBlock(groupBlock);
			compilator.setGroupBlock(groupBlock);
		} else {
			groupBlock = compilator.getGroupBlock();
		}
	}

	private boolean isGroupEmpty() {
		StringBuilder tmpGroup = new StringBuilder();
		buildGroupBlock(tmpGroup);
		return tmpGroup.length() == compilator.getGroupBlock().length();
	}
}
