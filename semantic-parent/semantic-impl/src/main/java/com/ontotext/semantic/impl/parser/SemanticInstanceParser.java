package com.ontotext.semantic.impl.parser;

import static com.ontotext.semantic.core.common.SemanticInstanceUtil.isValueInstance;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.COLLON;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.COMMA;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.CURLY_BRACE_CLOSE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.CURLY_BRACE_OPEN;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.ESCAPED_QUOTE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SINGLE_SPACE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SQUARE_BRACE_CLOSE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SQUARE_BRACE_OPEN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.vocabulary.XMLSchema;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ontotext.semantic.api.enumeration.SemanticInstanceType;
import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.parser.InstanceParser;
import com.ontotext.semantic.impl.instance.SemanticInstance;
import com.ontotext.semantic.impl.instance.SemanticLiteral;

/**
 * Default implementation for the instance parser interface
 * 
 * @author Svetlozar
 */
public class SemanticInstanceParser implements InstanceParser {

	private static final int CAPACITY = 256;

	private JsonParser parser = new JsonParser();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public String toString(Instance instance) {
		StringBuilder stringBuilder = new StringBuilder(CAPACITY);
		parseToString(instance, stringBuilder);

		// Build the JSON object and apply pretty format
		JsonObject json = parser.parse(stringBuilder.toString()).getAsJsonObject();
		return gson.toJson(json);
	}

	@Override
	public String toString(List<Instance> instances) {
		StringBuilder stringBuilder = new StringBuilder(CAPACITY * instances.size());

		stringBuilder.append(SQUARE_BRACE_OPEN);
		for (int i = 0; i < instances.size(); i++) {
			Instance instance = instances.get(i);
			parseToString(instance, stringBuilder);
			if (i < instances.size() - 1) {
				stringBuilder.append(COMMA);
			}
		}
		stringBuilder.append(SQUARE_BRACE_CLOSE);

		// Build the JSON array and apply pretty format
		JsonArray json = parser.parse(stringBuilder.toString()).getAsJsonArray();
		return gson.toJson(json);
	}

	@Override
	public Instance fromString(String instance) {
		JsonElement element = parser.parse(instance);
		JsonObject json = element.getAsJsonObject();
		Entry<String, JsonElement> first = json.entrySet().iterator().next();

		// First element of the JSON object should be the instance
		Instance instanceSource = new SemanticInstance(first.getKey());
		// Recursively unwrap the value of the first object's property
		List<Instance> instanceProperties = convert(instanceSource, first.getValue(), 0);
		// Attach any left over properties after the conversion
		for (Instance property : instanceProperties) {
			instanceSource.insertProperty(property, (Instance) null);
		}
		return instanceSource;
	}

	@Override
	public List<Instance> fromStringArray(String instance) {
		JsonElement element = parser.parse(instance);
		JsonArray array = element.getAsJsonArray();

		List<Instance> result = new ArrayList<>();
		for (JsonElement json : array) {
			JsonObject object = json.getAsJsonObject();
			result.add(fromString(object.toString()));
		}
		return result;
	}

	/**
	 * Converts a JsonElement value to a list if Instance values
	 * 
	 * @param source
	 *            the source at which to attach the values
	 * @param value
	 *            the JSON element from which to convert to instance or literal values
	 * @param level
	 *            the current level of traversal - level determines if we look for property or instance
	 * @return the list of converted instances
	 */
	private static List<Instance> convert(Instance source, JsonElement value, int level) {
		if (value.isJsonObject()) {
			JsonObject object = value.getAsJsonObject();
			List<Instance> values = new ArrayList<>();
			for (Entry<String, JsonElement> pair : object.entrySet()) {
				Instance instance = new SemanticInstance(pair.getKey());
				List<Instance> instValues = convert(instance, pair.getValue(), level + 1);

				if (!isPropertyLevel(level)) {
					source.insertProperty(instance, instValues);
				} else {
					values.add(instance);
				}
			}
			return values;
		} else if (value.isJsonArray()) {
			JsonArray arry = value.getAsJsonArray();
			List<Instance> values = new ArrayList<>();
			for (JsonElement element : arry) {
				values.addAll(convert(source, element, level));
			}
			return values;
		} else {
			return Arrays.asList(new SemanticLiteral(value.getAsString()));
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
		// Append JSON object beginning brace
		builder.append(CURLY_BRACE_OPEN);

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
		} else {
			// Append as empty JSON object
			builder.append(COLLON);
			builder.append(CURLY_BRACE_OPEN);
			builder.append(CURLY_BRACE_CLOSE);
		}
		// Append JSON object finishing brace
		builder.append(CURLY_BRACE_CLOSE);
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
		Value value = instance.getInstanceValue();
		boolean shouldWrap = isValueInstance(value);

		if (value instanceof Literal) {
			Literal literal = (Literal) value;
			URI type = literal.getDatatype();
			if (type.equals(XMLSchema.STRING)) {
				shouldWrap = true;
			}
		}

		if (shouldWrap) {
			return ESCAPED_QUOTE + instance + ESCAPED_QUOTE;
		}
		return instance.toString();
	}

	private static boolean isPropertyLevel(int level) {
		return (level % 2 == 0) ? false : true;
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
