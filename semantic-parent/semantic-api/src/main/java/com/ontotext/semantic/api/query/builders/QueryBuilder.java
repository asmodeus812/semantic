package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.query.appenders.QueryStatementAppender;

/**
 * Interface used to build the base statement part of a given query
 * 
 * @author Svetlozar
 */
public interface QueryBuilder extends Builder, QueryStatementAppender<QueryStatementBuilder> {
}
