package com.ontotext.semantic.impl.common;

import static com.ontotext.semantic.core.common.SemanticQueryUtil.concatVarSymbol;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.OBJECT;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.PREDICATE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SUBJECT;

import org.openrdf.model.vocabulary.RDF;

import com.ontotext.semantic.api.enumeration.ArithmeticOperators;
import com.ontotext.semantic.api.enumeration.LogicalOperators;
import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;
import com.ontotext.semantic.impl.query.builders.SemanticQueryBuilder;

/**
 * Utility class containing most of the commonly used semantic queries and their variations
 * 
 * @author Svetlozar
 */
public class SemanticPrebuiltQuery {

	/**
	 * The rdf:type of the instance
	 */
	public static final String TYPE = "type";

	/**
	 * The actual value of the instance
	 */
	public static final String VALUE = "value";

	/**
	 * The base class of all instances
	 */
	public static final String CLASS = "framework:class";

	/**
	 * The base property of all properties
	 */
	public static final String PROPERTY = "framework:property";

	/**
	 * Private constructor for utility classes
	 */
	private SemanticPrebuiltQuery() {
	}

	/**
	 * 
	 * Builds a semantic select query listing all type class instances. An instance filter is available under the ?value
	 * parameter
	 * 
	 * @return the semantic select query
	 */
	public static QueryCompiler buildSemanticSelectQuery() {
		String type = concatVarSymbol(TYPE);
		String value = concatVarSymbol(VALUE);

		return new SemanticQueryBuilder(SemanticQueryType.SELECT_DISTINCT)
				.appendStatement(SUBJECT, PREDICATE, OBJECT)
				.appendCondition(SUBJECT, PREDICATE, OBJECT)
				.appendCondition(SUBJECT, RDF.TYPE, type)
				.appendCondition(type, RDF.TYPE, CLASS)
				.appendCondition(PREDICATE, RDF.TYPE, "?m")
				.appendCondition("?m", RDF.TYPE, PROPERTY)
				.appendFilter(SUBJECT, ArithmeticOperators.EQUALS, value)
				.compile();
	}

	/**
	 * Builds a semantic delete query for a given ?type and ?value
	 * 
	 * @return the semantic delete query
	 */
	public static QueryCompiler buildSemanticDeleteQuery() {
		return new SemanticQueryBuilder(SemanticQueryType.DELETE)
				.appendStatement(SUBJECT, PREDICATE, OBJECT)
				.appendCondition(SUBJECT, PREDICATE, OBJECT)
				.appendFilter(SUBJECT, ArithmeticOperators.EQUALS, concatVarSymbol(VALUE))
				.appendLogicalOperator(LogicalOperators.OR)
				.appendFilter(OBJECT, ArithmeticOperators.EQUALS, concatVarSymbol(VALUE))
				.compile();
	}

	/**
	 * Builds a semantic data query
	 * 
	 * @param type
	 *            the type of the data query - INSERT / DELETE
	 * @return the semantic data query
	 */
	public static QueryCompiler buildSemanticDataQuery(SemanticQueryType type) {
		return new SemanticQueryBuilder(type)
				.appendStatement(SUBJECT, PREDICATE, OBJECT)
				.compile();
	}
}
