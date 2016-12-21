package com.ontotext.semantic.core.common;

import java.util.UUID;

import org.openrdf.model.Statement;

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

	public static final String DASH = "-";
	public static final String COLLON = ":";
	public static final String HASHTAG = "#";
	public static final String TYPECAST = "^^";

	public static final String EMPTY_STRING = "";
	public static final String SINGLE_SPACE = " ";
	public static final String DOUBLE_SPACE = "  ";
	public static final String ESCAPED_QUOTE = "\"";

	public static final String BRACE_OPEN = "(";
	public static final String BRACE_CLOSE = ")";

	public static final String ANGLE_BRACE_OPEN = "<";
	public static final String ANGLE_BRACE_CLOSE = ">";

	public static final String CURLY_BRACE_OPEN = SINGLE_SPACE + "{";
	public static final String CURLY_BRACE_CLOSE = SINGLE_SPACE + "}";

	public static final String VARSYMBOL = "?";
	public static final String VARIABLE = VARSYMBOL + "variable";
	public static final String SUBJECT = VARSYMBOL + "subject";
	public static final String PREDICATE = VARSYMBOL + "predicate";
	public static final String OBJECT = VARSYMBOL + "object";
	public static final String TRIPLET = SUBJECT + SINGLE_SPACE + PREDICATE + SINGLE_SPACE + OBJECT;

	public static final String WHERE = "WHERE";
	public static final String SELECT = "SELECT";
	public static final String DELETE = "DELETE";
	public static final String INSERT = "INSERT";
	public static final String FILTER = "FILTER";

	public static final String SELECT_TRIPLET = SELECT + SINGLE_SPACE + TRIPLET;
	public static final String DELETE_TRIPLET = DELETE + CURLY_BRACE_OPEN + TRIPLET + CURLY_BRACE_CLOSE;
	public static final String INSERT_TRIPLET = INSERT + CURLY_BRACE_OPEN + TRIPLET + CURLY_BRACE_CLOSE;

	/**
	 * Private constructor for utility class
	 */
	private SemanticSearchUtil() {
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
	 * Builds a where clause from a given list of string statements
	 * 
	 * @param statements
	 *            the list of statements as a string
	 * @return the built where clause block
	 */
	public static String buildWhereClause(String... statements) {
		StringBuilder sb = new StringBuilder(256);
		sb.append(SINGLE_SPACE).append(WHERE).append(CURLY_BRACE_OPEN);
		for (String string : statements) {
			sb.append(string).append(SINGLE_SPACE);
		}
		sb.append(CURLY_BRACE_CLOSE);
		return sb.toString();
	}

	/**
	 * Builds a filter clause from a given list of string statements
	 * 
	 * @param statements
	 *            the list of statements as a string
	 * @return the built filter clause block
	 */
	public static String buildFilterClause(String... statements) {
		StringBuilder sb = new StringBuilder(256);
		sb.append(SINGLE_SPACE).append(FILTER).append(BRACE_OPEN);
		for (String string : statements) {
			sb.append(string).append(SINGLE_SPACE);
		}
		sb.append(BRACE_CLOSE);
		return sb.toString();
	}

	/**
	 * Builds a statement clause from a given list of string statements
	 * 
	 * @param statements
	 *            the list of statements as a string
	 * @return the built statement clause
	 */
	public static String buildStatementClause(Statement... statements) {
		StringBuilder sb = new StringBuilder(256);
		for (Statement statement : statements) {
			sb.append(statement.getSubject()).append(SINGLE_SPACE);
			sb.append(statement.getPredicate()).append(SINGLE_SPACE);
			sb.append(statement.getObject()).append(SINGLE_SPACE);
		}
		return sb.toString();
	}

	public static String randomVariable() {
		return VARSYMBOL + UUID.randomUUID().toString();
	}
}
