package com.ontotext.semantic.impl.parser;

import static com.ontotext.semantic.core.common.SemanticInstanceUtil.parseToString;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.COMMA;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SQUARE_BRACE_CLOSE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SQUARE_BRACE_OPEN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

	private static boolean isPropertyLevel(int level) {
		return (level % 2 == 0) ? false : true;
	}

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

}
