package com.ontotext.semantic.core.common;

import static com.ontotext.semantic.api.enumeration.SemanticQueryOperators.FILTER;
import static com.ontotext.semantic.api.enumeration.SemanticQueryOperators.GROUP_BY;
import static com.ontotext.semantic.api.enumeration.SemanticQueryOperators.LIMIT;
import static com.ontotext.semantic.api.enumeration.SemanticQueryOperators.WHERE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.BRACE_CLOSE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.BRACE_OPEN;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.CURLY_BRACE_CLOSE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.CURLY_BRACE_OPEN;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.EMPTY_STRING;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SINGLE_SPACE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.VARSYMBOL;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openrdf.query.Binding;
import org.openrdf.query.Operation;
import org.openrdf.query.impl.BindingImpl;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;

/**
 * Utility class offering utility methods for working with SPARQL semantic queries
 * 
 * @author Svetlozar
 */
public class SemanticQueryUtil {

	/**
	 * Common parameter pattern for SPARQL variables matching. Matches a single parameter
	 */
	public static final Pattern PARAMETER_PATTERN = Pattern.compile("(\\?[a-zA-Z1-9]+)");

	/**
	 * Extracts all parameters from and for the SELECT clause of a query
	 * 
	 * @param query
	 *            the query from which to extract the parameters
	 * @return list of extracted parameters
	 */
	public static Set<String> extractQueryParams(String query) {
		Matcher paramterMatcher = PARAMETER_PATTERN.matcher(query);
		// use linked hash set to preserve order
		Set<String> parameters = new LinkedHashSet<String>();
		while (paramterMatcher.find()) {
			String param = paramterMatcher.group();
			// Trim the variable symbol (?) from the parameter
			parameters.add(stripVarSymbol(param));
		}
		return parameters;
	}

	/**
	 * Prepares all query parameters for a given parameter map
	 * 
	 * @param query
	 *            the query for which to bind the parameters
	 * @param parameterMap
	 *            the parameter map containing all parameter key value pairs
	 */
	public static <Q extends Operation> void prepareQueryParameters(Q query, Map<String, BindingImpl> parameterMap) {
		Collection<BindingImpl> bindings = parameterMap.values();
		for (Binding binding : bindings) {
			query.setBinding(binding.getName(), binding.getValue());
		}
	}

	/**
	 * Builds a filter block at the given builder
	 * 
	 * @param builder
	 *            the builder at which to build the filter block
	 * @return the builder
	 */
	public static StringBuilder buildFilterBlock(StringBuilder builder) {
		return builder.append(FILTER).append(BRACE_OPEN)
				.append(BRACE_CLOSE);
	}

	/**
	 * Finds the position at which an insertion can be performed for a filter block
	 * 
	 * @param builder
	 *            the builder from which to extrapolate the position
	 * @return the index of the position
	 */
	public static int findFilterAppendPosition(StringBuilder builder) {
		return builder.indexOf(BRACE_CLOSE);
	}

	/**
	 * Builds a where block at the given builder
	 * 
	 * @param builder
	 *            the builder at which to build the filter block
	 * @return the builder
	 */
	public static StringBuilder buildWhereBlock(StringBuilder builder) {
		return builder.append(SINGLE_SPACE).append(WHERE).append(SINGLE_SPACE).append(CURLY_BRACE_OPEN)
				.append(SINGLE_SPACE).append(CURLY_BRACE_CLOSE);
	}

	/**
	 * Finds the position at which an insertion can be performed for a where block
	 * 
	 * @param builder
	 *            the builder from which to extrapolate the position
	 * @return the index of the position
	 */
	public static int findWhereAppendPosition(StringBuilder builder) {
		return builder.indexOf(CURLY_BRACE_CLOSE);
	}

	/**
	 * Builds a group by block at the given builder
	 * 
	 * @param builder
	 *            the builder at which to build the group by block
	 * @return the builder
	 */
	public static StringBuilder buildGroupBlock(StringBuilder builder) {
		return builder.append(SINGLE_SPACE).append(GROUP_BY).append(SINGLE_SPACE);
	}

	/**
	 * Finds the position at which an insertion can be performed for a group by block
	 * 
	 * @param builder
	 *            the builder from which to extrapolate the position
	 * @return the index of the position
	 */
	public static int findGroupAppendPosition(StringBuilder builder) {
		String group = GROUP_BY.getValue() + SINGLE_SPACE;
		return builder.indexOf(group) + group.length();
	}

	/**
	 * Builds a limit block at the given builder
	 * 
	 * @param builder
	 *            the builder at which to build the limit block
	 * @return the builder
	 */
	public static StringBuilder buildLimitBlock(StringBuilder builder) {
		return builder.append(SINGLE_SPACE).append(LIMIT).append(SINGLE_SPACE);
	}

	/**
	 * Finds the position at which an insertion can be performed for a limit block
	 * 
	 * @param builder
	 *            the builder from which to extrapolate the position
	 * @return the index of the position
	 */
	public static int findLimitAppendPosition(StringBuilder builder) {
		String group = LIMIT.getValue() + SINGLE_SPACE;
		return builder.indexOf(group) + group.length();
	}

	/**
	 * Builds a statement block at the given builder
	 * 
	 * @param builder
	 *            the builder at which to build the filter block
	 * @return the builder
	 */
	public static StringBuilder buildStatementBlock(StringBuilder builder, SemanticQueryType type) {
		switch (type) {
		case SELECT:
			builder.append(type).append(SINGLE_SPACE);
			break;
		case INSERT:
		case DELETE:
		case INSERT_DATA:
		case DELETE_DATA:
			builder.append(type.getValue()).append(CURLY_BRACE_OPEN);
			builder.append(CURLY_BRACE_CLOSE);
			break;
		default:
			break;
		}
		return builder;
	}

	/**
	 * Finds the position at which an insertion can be performed for a statement block
	 * 
	 * @param builder
	 *            the builder from which to extrapolate the position
	 * @return the index of the position
	 */
	public static int findStatementAppendPosition(StringBuilder builder, SemanticQueryType type) {
		switch (type) {
		case SELECT:
			String typeStr = type.toString() + SINGLE_SPACE;
			return builder.indexOf(typeStr) + typeStr.length();
		case INSERT:
		case DELETE:
		case INSERT_DATA:
		case DELETE_DATA:
			return builder.indexOf(CURLY_BRACE_CLOSE);
		default:
			return -1;
		}
	}

	/**
	 * Builds an array of serializables as a plain string of the values with a given delimiter
	 * 
	 * @param delimiter
	 *            the delimiter between values
	 * @param serializables
	 *            the serializables
	 * @return the built string
	 */
	public static String buildToString(String delimiter, Serializable... serializables) {
		StringBuilder sb = new StringBuilder(256);
		for (Serializable serializable : serializables) {
			sb.append(serializable).append(SemanticSparqlUtil.SINGLE_SPACE);
		}
		return sb.toString();
	}

	/**
	 * Builds an array of serializables as a plain string of the values with a space delimiter
	 * 
	 * @param serializables
	 *            the serializables
	 * @return the built string
	 */
	public static String buildToString(Serializable... serializables) {
		return buildToString(SemanticSparqlUtil.SINGLE_SPACE, serializables);
	}

	/**
	 * Strips all variable symbols from the given string. Based on SPARQL syntax
	 * 
	 * @param variable
	 *            the variable from which to be stripped
	 * @return the new modified value that does not contain the variable symbol
	 */
	public static String stripVarSymbol(String variable) {
		return variable.replace(VARSYMBOL, EMPTY_STRING);
	}

	/**
	 * Generates a random UUID string & convert it to a SPARQL variable
	 * 
	 * @return the random SPARQL variable
	 */
	public static String randomVariable() {
		return VARSYMBOL + UUID.randomUUID().toString();
	}

	/**
	 * Checks if a given query type supports the condition or the filter type blocks. Based on SPARQL syntax
	 * 
	 * @param type
	 *            the type of the semantic query
	 * @return true if supports, false otherwise
	 */
	public static boolean isSupportingConditionBlocks(SemanticQueryType type) {
		return type != SemanticQueryType.INSERT_DATA && type != SemanticQueryType.DELETE_DATA;
	}

	/**
	 * Checks if a given query type supports the group by clause. Based on SPARQL syntax
	 * 
	 * @param type
	 *            the type of the query
	 * @return true if supports, false otherwise
	 */
	public static boolean isSupportingGroupBlocks(SemanticQueryType type) {
		return type == SemanticQueryType.SELECT;
	}

	/**
	 * Checks if a given query type supports the limit clause. Based on SPARQL syntax
	 * 
	 * @param type
	 *            the type of the query
	 * @return true if supports, false otherwise
	 */
	public static boolean isSupportingLimitBlocks(SemanticQueryType type) {
		return type == SemanticQueryType.SELECT;
	}

}
