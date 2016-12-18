package com.ontotext.semantic.core.common;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class offering utility methods for working with SPARQL semantic queries
 * 
 * @author Svetlozar
 */
public class SemanticQueryUtil {

	/**
	 * Common parameter pattern for SPARQL variables matching
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
			// Trim the variable character (?) from the parameter
			parameters.add(param.substring(1));
		}
		return parameters;
	}
}
