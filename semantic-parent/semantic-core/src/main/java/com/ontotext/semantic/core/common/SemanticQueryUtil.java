package com.ontotext.semantic.core.common;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openrdf.query.Binding;
import org.openrdf.query.Operation;
import org.openrdf.query.impl.BindingImpl;

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
}
