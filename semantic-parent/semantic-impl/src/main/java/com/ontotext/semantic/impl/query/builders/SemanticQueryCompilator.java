package com.ontotext.semantic.impl.query.builders;

import static com.ontotext.semantic.core.common.SemanticNamespaceUtil.parseToRawNamespace;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.findWhereAppendPosition;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.query.builders.QueryBlockCompilator;

/**
 * Semantic query compilator. Compiles all basic blocks of a semantic query
 * 
 * @author Svetlozar
 */
public class SemanticQueryCompilator implements QueryBlockCompilator {

	private SemanticQueryType type;
	private StringBuilder whereBlock = new StringBuilder(256);
	private StringBuilder filterBlock = new StringBuilder(256);
	private StringBuilder statementBlock = new StringBuilder(256);

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
	public String getShortFormatQuery() {
		return compileQuery().toString();
	}
	@Override
	public String getLongFormatQuery() {
		return parseToRawNamespace(compileQuery().toString());
	}

	private String compileQuery() {
		StringBuilder compiled = new StringBuilder(512);
		compiled.append(whereBlock);
		int pos = findWhereAppendPosition(compiled);
		compiled.insert(pos, filterBlock);
		compiled.insert(0, statementBlock);
		return compiled.toString();
	}
}
