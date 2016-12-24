package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.common.Constructor;
import com.ontotext.semantic.api.query.appenders.QueryConditionAppender;
import com.ontotext.semantic.api.query.appenders.QueryStatementAppender;

/**
 * Interface for query statement and condition building
 * 
 * @author Svetlozar
 */
public interface QueryStatementBuilder extends Builder, Constructor, QueryStatementAppender<QueryStatementBuilder>,
		QueryConditionAppender<QueryConditionBuilder>, QueryCompilerBuilder {
}
