package com.ontotext.semantic.core.common;

import static com.ontotext.semantic.core.common.SemanticSparqlUtil.COLLON;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.COMMA;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.CURLY_BRACE_CLOSE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.CURLY_BRACE_OPEN;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SINGLE_SPACE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SQUARE_BRACE_CLOSE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SQUARE_BRACE_OPEN;

import java.util.ArrayList;
import java.util.Map;

import org.openrdf.model.BNode;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

import com.ontotext.semantic.api.enumeration.SemanticInstanceType;
import com.ontotext.semantic.api.instance.Instance;

/**
 * Semantic instance utility class
 * 
 * @author Svetlozar
 */
public class SemanticInstanceUtil {

	/**
	 * Private constructor for utility class
	 */
	private SemanticInstanceUtil() {

	}

	/**
	 * Checks if a given value is an instance (property or an object) or a literal, true for instance or false for
	 * literal or BNode
	 * 
	 * @param value
	 *            the value to be checked
	 * @return true if instance (property or an object), false otherwise
	 */
	public static boolean isValueInstance(Value value) {
		return (value instanceof URI) || (value instanceof BNode);
	}

	/**
	 * Extracts the actual value from an URI, BNode or a Literal using the super interface Value
	 * 
	 * @param value
	 *            the value from which to extract
	 * @return the extracted value as a string
	 */
	public static String extractActualValue(Value value) {
		if (value instanceof URI) {
			return ((URI) value).getLocalName();
		} else if (value instanceof BNode) {
			return ((BNode) value).getID();
		} else {
			return ((Literal) value).getLabel();
		}
	}

	/**
	 * Parses a given instance as a complete JSON formated string
	 * 
	 * @param instance
	 *            the instance to be parsed
	 * @param builder
	 *            the builder where the formated JSON string will be stored
	 */
	public static void parseToString(Instance instance, StringBuilder builder) {
		boolean appendBrace = instance.getPropertyMap() != null && instance.getPropertyMap().size() != 0;

		// Append JSON object beginning brace
		if (appendBrace) {
			builder.append(CURLY_BRACE_OPEN);
		}

		// Append the instance value wrapped around quotes
		builder.append(wrapInQuotes(instance));
		if (instance.getPropertyMap().size() != 0) {
			boolean hasPropValues = hasPropertyValues(instance);
			// Append begin block according to the instance properties state
			appendBeginBlock(builder, hasPropValues);
			// Append the parsed instance properties
			parseProperties(instance, builder);
			// Append finish block according to the instance properties state
			appendFinishBlock(builder, hasPropValues);
		}

		// Append JSON object finishing brace
		if (appendBrace) {
			builder.append(CURLY_BRACE_CLOSE);
		}
	}

	private static void parseProperties(Instance instance, StringBuilder builder) {
		int currentProperty = 0;

		for (Map.Entry<Instance, ArrayList<Instance>> pair : instance.getPropertyMap().entrySet()) {
			int currentInstance = 0;

			++currentProperty;
			builder.append(wrapInQuotes(pair.getKey()));

			if (pair.getValue().size() != 0) {
				builder.append(COLLON).append(SINGLE_SPACE);
				builder.append(SQUARE_BRACE_OPEN);
				for (Instance value : pair.getValue()) {
					++currentInstance;
					if (value.getInstanceType() == SemanticInstanceType.INSTANCE) {
						parseToString(value, builder);
					} else {
						builder.append(wrapInQuotes(value));
					}

					if (currentInstance < pair.getValue().size()) {
						builder.append(COMMA);
					}
				}
				builder.append(SQUARE_BRACE_CLOSE);
			}

			if (currentProperty < instance.getPropertyMap().size()) {
				builder.append(COMMA);
			}
			builder.append(System.lineSeparator());
		}
	}

	private static void appendBeginBlock(StringBuilder builder, boolean hasPropertyValues) {
		builder.append(SINGLE_SPACE).append(COLLON);
		if (hasPropertyValues) {
			builder.append(CURLY_BRACE_OPEN);
		} else {
			builder.append(SQUARE_BRACE_OPEN);
		}
		builder.append(System.lineSeparator());
	}

	private static void appendFinishBlock(StringBuilder builder, boolean hasPropertyValues) {
		if (hasPropertyValues) {
			builder.append(CURLY_BRACE_CLOSE);
		} else {
			builder.append(SQUARE_BRACE_CLOSE);
		}
		builder.append(SINGLE_SPACE);
	}

	private static String wrapInQuotes(Instance instance) {
		return "\"" + instance.toString() + "\"";
	}

	private static boolean hasPropertyValues(Instance instance) {
		for (Map.Entry<Instance, ArrayList<Instance>> pair : instance.getPropertyMap().entrySet()) {
			if (pair.getValue().size() != 0) {
				return true;
			}
		}
		return false;
	}

}
