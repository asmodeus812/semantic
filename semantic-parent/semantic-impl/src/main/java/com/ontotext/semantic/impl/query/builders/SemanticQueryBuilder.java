package com.ontotext.semantic.impl.query.builders;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.query.builders.QueryBlockCompilator;
import com.ontotext.semantic.api.query.builders.QueryBuilder;
import com.ontotext.semantic.api.query.builders.QueryStatementBuilder;
import com.ontotext.semantic.api.structures.Triplet;

/**
 * Semantic query base builder
 * 
 * @author Svetlozar
 */
public class SemanticQueryBuilder implements QueryBuilder {

	private SemanticQueryType type;
	private QueryBlockCompilator compilator;

	/**
	 * Initialize a semantic query builder
	 * 
	 * @param type
	 *            the type of the query
	 */
	public SemanticQueryBuilder(SemanticQueryType type) {
		this.type = type;
		compilator = new SemanticQueryCompilator();
	}

	@Override
	public QueryStatementBuilder appendStatement(Triplet statement) {
		compilator.setType(type);
		QueryStatementBuilder stateBuilder = new SemanticStatementBuilder(compilator);
		stateBuilder.appendStatement(statement);
		return stateBuilder;
	}

}
