package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.query.appenders.QueryGroupAppender;

/**
 * Interface for query group by clause building
 * 
 * @author Svetlozar
 */
public interface QueryGroupBuilder extends Builder, QueryGroupAppender<QueryLimitBuilder>, QueryCompilerBuilder {
}
