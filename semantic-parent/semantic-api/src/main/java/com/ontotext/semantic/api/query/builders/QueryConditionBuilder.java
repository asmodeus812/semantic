package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.query.appenders.QueryConditionAppender;
import com.ontotext.semantic.api.query.appenders.QueryFilterAppender;
import com.ontotext.semantic.api.query.appenders.QueryGroupAppender;
import com.ontotext.semantic.api.query.appenders.QueryLimitAppender;

/**
 * Interface for building a given conditions to a query
 * 
 * @author Svetlozar
 */
public interface QueryConditionBuilder
		extends Builder, QueryConditionAppender<QueryConditionBuilder>,
		QueryFilterAppender<QueryOperatorBuilder>, QueryGroupAppender<QueryLimitBuilder>,
		QueryLimitAppender<QueryGroupBuilder>, QueryCompilerBuilder {
}
