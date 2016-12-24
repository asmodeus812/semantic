package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;

/**
 * Interface serving as
 * 
 * @author Svetlozar
 */
public interface QueryCompilerBuilder extends Builder {

	/**
	 * Gets the query compiler proxy service interface
	 * 
	 * @return the query compilator proxy service interface
	 */
	public QueryCompiler compile();
}
