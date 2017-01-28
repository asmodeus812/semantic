package com.ontotext.semantic.impl.query.builders;

import java.io.Serializable;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.query.builders.QueryBuilder;
import com.ontotext.semantic.api.query.builders.QueryStatementBuilder;
import com.ontotext.semantic.api.query.compiler.QueryBlockCompiler;
import com.ontotext.semantic.impl.query.compiler.SemanticQueryCompiler;

/**
 * Semantic query base builder
 * 
 * @author Svetlozar
 */
public class SemanticQueryBuilder implements QueryBuilder {

	private SemanticQueryType type;
	private QueryBlockCompiler compilator;

	/**
	 * Initialize a semantic query builder
	 * 
	 * @param type
	 *            the type of the query
	 */
	public SemanticQueryBuilder(SemanticQueryType type) {
		this.type = type;
		build();
	}

	@Override
	public void build() {
		compilator = new SemanticQueryCompiler();
		compilator.setType(type);
	}

	@Override
	public QueryStatementBuilder appendStatement(Serializable subject) {
		QueryStatementBuilder stateBuilder = new SemanticStatementBuilder(compilator);
		stateBuilder.appendStatement(subject);
		return stateBuilder;
	}

	@Override
	public QueryStatementBuilder appendStatement(Serializable subject, Serializable predicate) {
		QueryStatementBuilder stateBuilder = new SemanticStatementBuilder(compilator);
		stateBuilder.appendStatement(subject, predicate);
		return stateBuilder;
	}

	@Override
	public QueryStatementBuilder appendStatement(Serializable subject, Serializable predicate, Serializable object) {
		QueryStatementBuilder stateBuilder = new SemanticStatementBuilder(compilator);
		stateBuilder.appendStatement(subject, predicate, object);
		return stateBuilder;
	}
}
