package com.ontotext.semantic.impl.query.compiler;

import static com.ontotext.semantic.core.common.SemanticNamespaceUtil.parseToRawNamespace;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.findWhereAppendPosition;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.isSupportingConditionBlocks;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.isSupportingGroupBlocks;
import static com.ontotext.semantic.core.common.SemanticQueryUtil.isSupportingLimitBlocks;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.query.compiler.QueryBlockCompiler;

/**
 * Semantic query compilator. Compiles all basic blocks of a semantic query
 * 
 * @author Svetlozar
 */
public class SemanticQueryCompiler implements QueryBlockCompiler {

	private SemanticQueryType type;
	private StringBuilder whereBlock;
	private StringBuilder groupBlock;
	private StringBuilder limitBlock;
	private StringBuilder filterBlock;
	private StringBuilder statementBlock;
	private StringBuilder optionalBlock;

	/**
	 * Initializes an empty compiler
	 */
	public SemanticQueryCompiler() {
		// Empty constructor
	}

	/**
	 * Initializes a basic query compiler using all basic blocks of a semantic query
	 * 
	 * @param type
	 *            the type of the query
	 * @param whereBlock
	 *            the where block of the query
	 * @param filterBlock
	 *            the filter block of the query
	 * @param statementBlock
	 *            the statement block of the query
	 */
	public SemanticQueryCompiler(SemanticQueryType type, StringBuilder whereBlock, StringBuilder filterBlock,
			StringBuilder statementBlock) {
		super();
		this.type = type;
		this.whereBlock = whereBlock;
		this.filterBlock = filterBlock;
		this.statementBlock = statementBlock;
	}

	@Override
	public SemanticQueryType getType() {
		return type;
	}

	@Override
	public void setType(SemanticQueryType type) {
		this.type = type;
	}

	@Override
	public StringBuilder getWhereBlock() {
		return whereBlock;
	}

	@Override
	public void setWhereBlock(StringBuilder whereBlock) {
		this.whereBlock = whereBlock;
	}

	@Override
	public StringBuilder getFilterBlock() {
		return filterBlock;
	}

	@Override
	public void setFilterBlock(StringBuilder filterBlock) {
		this.filterBlock = filterBlock;
	}

	@Override
	public StringBuilder getStatementBlock() {
		return statementBlock;
	}

	@Override
	public void setStatementBlock(StringBuilder statementBlock) {
		this.statementBlock = statementBlock;
	}

	@Override
	public void setGroupBlock(StringBuilder groupBlock) {
		this.groupBlock = groupBlock;
	}

	@Override
	public StringBuilder getGroupBlock() {
		return groupBlock;
	}

	@Override
	public void setLimitBlock(StringBuilder limitBlock) {
		this.limitBlock = limitBlock;
	}

	@Override
	public StringBuilder getLimitBlock() {
		return limitBlock;
	}

	@Override
	public void setOptionalBlock(StringBuilder optionalBlock) {
		this.optionalBlock = optionalBlock;
	}

	@Override
	public StringBuilder getOptionalBlock() {
		return optionalBlock;
	}

	@Override
	public String shortFormatQuery() {
		return compileQuery().toString();
	}

	@Override
	public String longFormatQuery() {
		return parseToRawNamespace(shortFormatQuery());
	}

	private String compileQuery() {
		initializeQueryBlocks();
		StringBuilder compiled = new StringBuilder(512);

		if (isSupportingConditionBlocks(type)) {
			compiled.append(whereBlock);
			int pos = findWhereAppendPosition(compiled);
			compiled.insert(pos, optionalBlock);
			compiled.insert(pos + optionalBlock.length(), filterBlock);
			if (isSupportingGroupBlocks(type)) {
				compiled.append(groupBlock);
			}
			if (isSupportingLimitBlocks(type)) {
				compiled.append(limitBlock);
			}
		}
		compiled.insert(0, statementBlock);
		return compiled.toString();
	}

	private void initializeQueryBlocks() {
		whereBlock = (whereBlock == null) ? new StringBuilder(32) : whereBlock;
		groupBlock = (groupBlock == null) ? new StringBuilder(32) : groupBlock;
		limitBlock = (limitBlock == null) ? new StringBuilder(32) : limitBlock;
		filterBlock = (filterBlock == null) ? new StringBuilder(32) : filterBlock;
		statementBlock = (statementBlock == null) ? new StringBuilder(32) : statementBlock;
		optionalBlock = (optionalBlock == null) ? new StringBuilder(32) : optionalBlock;
	}

}
