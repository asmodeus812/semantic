package com.ontotext.semantic.core.common;

import java.util.UUID;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;

/**
 * Utility class offering utility constants or methods for semantic searches with SPARQL
 * 
 * @author Svetlozar
 */
public class SemanticSearchUtil {

	/**
	 * SPARQL specific constants and expressions
	 * 
	 */
	public static final String DOT = ".";
	public static final String DASH = "-";
	public static final String COLLON = ":";
	public static final String HASHTAG = "#";
	public static final String TYPECAST = "^^";

	public static final String EMPTY_STRING = "";
	public static final String SINGLE_SPACE = " ";
	public static final String DOUBLE_SPACE = "  ";
	public static final String ESCAPED_QUOTE = "\"";

	public static final String BRACE_OPEN = SINGLE_SPACE + "(";
	public static final String BRACE_CLOSE = SINGLE_SPACE + ")";

	public static final String ANGLE_BRACE_OPEN = "<";
	public static final String ANGLE_BRACE_CLOSE = ">";

	public static final String CURLY_BRACE_OPEN = SINGLE_SPACE + "{";
	public static final String CURLY_BRACE_CLOSE = SINGLE_SPACE + "}";

	public static final String VARSYMBOL = "?";
	public static final String VARIABLE = VARSYMBOL + "variable";
	public static final String SUBJECT = VARSYMBOL + "subject";
	public static final String PREDICATE = VARSYMBOL + "predicate";
	public static final String OBJECT = VARSYMBOL + "object";

	public static final String WHERE = "WHERE";
	public static final String FILTER = "FILTER";
	public static final String GROUP_BY = "GROUP BY";

	/**
	 * Private constructor for utility class
	 */
	private SemanticSearchUtil() {
	}

	/**
	 * Builds a filter block at the given builder
	 * 
	 * @param builder
	 *            the builder at which to build the filter block
	 * @return the builder
	 */
	public static StringBuilder buildFilterBlock(StringBuilder builder) {
		return builder.append(SINGLE_SPACE).append(FILTER).append(BRACE_OPEN).append(BRACE_CLOSE);
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
		return builder.append(SINGLE_SPACE).append(WHERE).append(CURLY_BRACE_OPEN).append(CURLY_BRACE_CLOSE);
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
			String typeStr = type.toString();
			return builder.indexOf(typeStr) + typeStr.length();
		case INSERT:
		case DELETE:
		case INSERT_DATA:
		case DELETE_DATA:
			return builder.indexOf(CURLY_BRACE_CLOSE);
		default:
			break;
		}
		return -1;
	}

	/**
	 * Strips all question marks variable symbols from the given string. Based on SPARQL syntax
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
	 * Checks if a given query type supports the condition or the filter type blocks
	 * 
	 * @param type
	 *            the type of the semantic query
	 * @return true if supports, false otherwise
	 */
	public static boolean isSupportingConditionBlocks(SemanticQueryType type) {
		return type != SemanticQueryType.INSERT_DATA && type != SemanticQueryType.DELETE_DATA;
	}
}
