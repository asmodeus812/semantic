package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.query.appenders.QueryLimitAppender;

/**
 * Interface for query limit clause building
 * 
 * @author Svetlozar
 */
public interface QueryLimitBuilder extends Builder, QueryLimitAppender<QueryGroupBuilder>, QueryCompilerBuilder {
}
