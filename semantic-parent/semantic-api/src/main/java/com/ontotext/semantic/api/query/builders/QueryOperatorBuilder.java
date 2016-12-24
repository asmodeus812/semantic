package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.query.appenders.QueryGroupAppender;
import com.ontotext.semantic.api.query.appenders.QueryLimitAppender;
import com.ontotext.semantic.api.query.appenders.QueryOperatorAppender;

/**
 * Interface building an operator to a given query
 * 
 * @author Svetlozar
 */
public interface QueryOperatorBuilder extends Builder, QueryOperatorAppender<QueryFilterBuilder>,
		QueryGroupAppender<QueryLimitBuilder>, QueryLimitAppender<QueryGroupBuilder>,
		QueryCompilerBuilder {
}
