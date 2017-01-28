package com.ontotext.semantic.api.query.compiler;

/**
 * Interface serving as a proxy for semantic query compilation
 * 
 * @author Svetlozar
 */
public interface QueryCompiler {

	/**
	 * Compile the query in a short format
	 * 
	 * @return the short format of the query
	 */
	public String shortFormatQuery();

	/**
	 * Compile the query in a long format
	 * 
	 * @return the long format of the query
	 */
	public String longFormatQuery();
}
