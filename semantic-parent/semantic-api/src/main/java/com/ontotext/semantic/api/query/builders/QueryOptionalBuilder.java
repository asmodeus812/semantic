package com.ontotext.semantic.api.query.builders;

import com.ontotext.semantic.api.common.Builder;
import com.ontotext.semantic.api.query.appenders.QueryConditionAppender;
import com.ontotext.semantic.api.query.appenders.QueryOptionalAppender;

public interface QueryOptionalBuilder
		extends Builder, QueryOptionalAppender<QueryOptionalBuilder>, QueryConditionAppender<QueryConditionBuilder>,
		QueryCompilerBuilder {

}
