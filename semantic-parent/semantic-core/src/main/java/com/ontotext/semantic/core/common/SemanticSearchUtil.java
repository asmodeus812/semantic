package com.ontotext.semantic.core.common;

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

	public static final String VARIABLE = "?";
	public static final String INSTANCE = VARIABLE + "instance";
	public static final String PREDICATE = VARIABLE + "predicate";
	public static final String OBJECT = VARIABLE + "object";

	/**
	 * Private constructor for utility class
	 */
	private SemanticSearchUtil() {
	}

}
