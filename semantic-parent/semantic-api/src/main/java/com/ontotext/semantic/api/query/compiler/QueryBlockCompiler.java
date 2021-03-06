package com.ontotext.semantic.api.query.compiler;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;

/**
 * Interface used for query compilation based on the basic blocks of any query
 * 
 * @author Svetlozar
 */
public interface QueryBlockCompiler extends QueryCompiler {

	/**
	 * Get the type of the given query
	 * 
	 * @param type
	 *            the type of the query
	 */
	public void setType(SemanticQueryType type);

	/**
	 * Set the condition block of the query
	 * 
	 * @param whereBlock
	 *            the condition block of the query
	 */
	public void setWhereBlock(StringBuilder whereBlock);

	/**
	 * Set the filter block of the query
	 * 
	 * @param filterBlock
	 *            the filter block of the query
	 */
	public void setFilterBlock(StringBuilder filterBlock);

	/**
	 * Set the group by block of the query
	 * 
	 * @param groupBlock
	 *            the group by block of the query
	 */
	public void setGroupBlock(StringBuilder groupBlock);

	/**
	 * Set the limit block of the query
	 * 
	 * @param limitBlock
	 *            the limit block of the query
	 */
	public void setLimitBlock(StringBuilder limitBlock);

	/**
	 * Set the statement block of the query
	 * 
	 * @param statementBlock
	 *            the statement block of the query
	 */
	public void setStatementBlock(StringBuilder statementBlock);

	/**
	 * Set the optional block of the query
	 * 
	 * @param optionalBlock
	 */
	public void setOptionalBlock(StringBuilder optionalBlock);

	/**
	 * Get the type of the query
	 * 
	 * @return the type of the query
	 */
	public SemanticQueryType getType();

	/**
	 * Get the where block of the query
	 * 
	 * @return the where block of the query
	 */
	public StringBuilder getWhereBlock();

	/**
	 * Get the statement block of the query
	 * 
	 * @return the statement block of the query
	 */
	public StringBuilder getStatementBlock();

	/**
	 * Get the optional block of the query
	 * 
	 * @return the optional block of the query
	 */
	public StringBuilder getOptionalBlock();

	/**
	 * Get the filter block of the query
	 * 
	 * @return the filter block of the query
	 */
	public StringBuilder getFilterBlock();

	/**
	 * Get the group by block of the query
	 * 
	 * @return the group by block of the query
	 */
	public StringBuilder getGroupBlock();

	/**
	 * Get the limit block of the query
	 * 
	 * @return the limit block of the query
	 */
	public StringBuilder getLimitBlock();
}
