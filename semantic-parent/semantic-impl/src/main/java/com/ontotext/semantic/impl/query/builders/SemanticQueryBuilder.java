package com.ontotext.semantic.impl.query.builders;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.query.builders.QueryBuilder;
import com.ontotext.semantic.api.query.builders.QueryStatementBuilder;
import com.ontotext.semantic.api.query.compiler.QueryBlockCompiler;
import com.ontotext.semantic.api.structures.Triplet;
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
		construct();
	}

	@Override
	public QueryStatementBuilder appendStatement(Triplet statement) {
		QueryStatementBuilder stateBuilder = new SemanticStatementBuilder(compilator);
		stateBuilder.appendStatement(statement);
		return stateBuilder;
	}

	@Override
	public void construct() {
		compilator = new SemanticQueryCompiler();
		compilator.setType(type);
	}

}
