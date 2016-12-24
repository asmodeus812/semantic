package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.query.appenders.QueryConditionAppender;
import com.ontotext.semantic.api.query.appenders.QueryGroupAppender;
import com.ontotext.semantic.api.query.appenders.QueryLimitAppender;
import com.ontotext.semantic.api.query.appenders.QueryStatementAppender;

/**
 * Interface for query statement and condition building
 * 
 * @author Svetlozar
 */
public interface QueryStatementBuilder extends Builder, QueryStatementAppender<QueryStatementBuilder>,
		QueryConditionAppender<QueryConditionBuilder>, QueryLimitAppender<QueryGroupBuilder>,
		QueryGroupAppender<QueryLimitBuilder>, QueryCompilerBuilder {
}
