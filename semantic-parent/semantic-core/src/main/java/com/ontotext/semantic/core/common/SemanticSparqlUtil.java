package com.ontotext.semantic.core.common;

/**
 * Utility class offering utility constants or methods for semantic searches with SPARQL
 * 
 * @author Svetlozar
 */
public class SemanticSparqlUtil {

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

	public static final String BRACE_OPEN = "(";
	public static final String BRACE_CLOSE = ")";

	public static final String ANGLE_BRACE_OPEN = "<";
	public static final String ANGLE_BRACE_CLOSE = ">";

	public static final String CURLY_BRACE_OPEN = "{";
	public static final String CURLY_BRACE_CLOSE = "}";

	public static final String VARSYMBOL = "?";
	public static final String VARIABLE = VARSYMBOL + "variable";
	public static final String SUBJECT = VARSYMBOL + "subject";
	public static final String PREDICATE = VARSYMBOL + "predicate";
	public static final String OBJECT = VARSYMBOL + "object";

	/**
	 * Private constructor for utility class
	 */
	private SemanticSparqlUtil() {
	}

}
