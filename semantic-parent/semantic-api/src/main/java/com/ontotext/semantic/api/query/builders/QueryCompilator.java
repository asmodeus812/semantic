package com.ontotext.semantic.api.query.builders;

/**
 * Interface serving as a proxy for semantic query compilation
 * 
 * @author Svetlozar
 */
public interface QueryCompilator {

	/**
	 * Compile the query in a short format
	 * 
	 * @return the short format of the query
	 */
	public String getShortFormatQuery();

	/**
	 * Compile the query in a long format
	 * 
	 * @return the long format of the query
	 */
	public String getLongFormatQuery();
}
