package com.ontotext.semantic.core.common;

import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SINGLE_SPACE;

import java.nio.CharBuffer;

/**
 * Class contatining all common utility methods and functions used by the core semantic engine
 * 
 * @author Svetlozar
 */
public class SemanticCommonUtil {

	/**
	 * Represents the End of string symbol '\0'
	 */
	public static final String END_OF_STRING = "\0";

	/**
	 * Generates a string of a given length containing only spaces
	 * 
	 * @param numElements
	 *            the number of spaces for the string to contain
	 * @return the built string
	 */
	public static String generateString(int numElements) {
		return generateString(numElements, SINGLE_SPACE.charAt(0));
	}

	/**
	 * Generates a string with a given number and elements
	 * 
	 * @param numElements
	 *            the number of times the element should be repeated
	 * @param element
	 *            the base element
	 * @return the built string
	 */
	public static String generateString(int numElements, char element) {
		return CharBuffer.allocate(numElements).toString().replace(END_OF_STRING, Character.toString(element));
	}

	/**
	 * Appends spaces on both sides of the source string
	 * 
	 * @param source
	 *            the source string
	 * @return the source string with appended spaces on both sides
	 */
	public static String wrapInSpaces(String source) {
		return SINGLE_SPACE + source + SINGLE_SPACE;
	}

	/**
	 * Replaces a given string between two indexes (start and end) inside the source string with a given replacement
	 * string
	 * 
	 * @param source
	 *            the source string
	 * @param start
	 *            the start from where to begin replacing
	 * @param end
	 *            the end of the replacement block
	 * @param replaceWith
	 *            the replacement string
	 * @return the processed source string with the replacement
	 */
	public static String replace(String source, int start, int end, String replaceWith) {
		return new StringBuilder(source).replace(start, end, replaceWith).toString();
	}
}
