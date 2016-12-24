package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.query.appenders.QueryFilterAppender;

/**
 * Interface for query filter building
 * 
 * @author Svetlozar
 */
public interface QueryFilterBuilder
		extends Builder, QueryFilterAppender<QueryOperatorBuilder> {
}
