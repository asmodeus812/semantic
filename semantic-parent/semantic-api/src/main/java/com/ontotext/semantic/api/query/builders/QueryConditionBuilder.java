package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.common.Constructor;
import com.ontotext.semantic.api.query.appenders.QueryConditionAppender;
import com.ontotext.semantic.api.query.appenders.QueryFilterAppender;

/**
 * Interface for building a given conditions to a query
 * 
 * @author Svetlozar
 */
public interface QueryConditionBuilder
		extends Builder, Constructor, QueryConditionAppender<QueryConditionBuilder>,
		QueryFilterAppender<QueryOperatorBuilder>, QueryCompilerBuilder {
}
