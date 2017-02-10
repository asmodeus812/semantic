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
		if (shouldAppendBrace(instance)) {
			if (shouldAppendComma(builder)) {
				builder.append(COMMA);
			}
			builder.append(CURLY_BRACE_OPEN);
		}
		builder.append(wrapInQuotes(instance));
		if (instance.getPropertyMap().size() != 0) {
			int currentProperty = 0;

			builder.append(SINGLE_SPACE).append(COLLON);
			builder.append(CURLY_BRACE_OPEN).append(System.lineSeparator());
			for (Map.Entry<Instance, ArrayList<Instance>> pair : instance.getPropertyMap().entrySet()) {
				++currentProperty;
				int currentInstance = 0;

				builder.append(wrapInQuotes(pair.getKey()));
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
				if (currentProperty < instance.getPropertyMap().size()) {
					builder.append(COMMA);
				}
				builder.append(System.lineSeparator());
			}
			builder.append(CURLY_BRACE_CLOSE).append(SINGLE_SPACE);
		}

		if (shouldAppendBrace(instance)) {
			builder.append(CURLY_BRACE_CLOSE);
		}
	}

	private static String wrapInQuotes(Instance instance) {
		return "\"" + instance.toString() + "\"";
	}

	private static boolean shouldAppendBrace(Instance instance) {
		return instance.getPropertyMap() != null && instance.getPropertyMap().size() != 0;
	}

	private static boolean shouldAppendComma(StringBuilder builder) {
		return builder.length() > 0 && builder.charAt(builder.length() - 1) != SQUARE_BRACE_OPEN.charAt(0);
	}

}
