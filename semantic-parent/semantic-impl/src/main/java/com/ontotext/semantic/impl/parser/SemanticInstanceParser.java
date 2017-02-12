package com.ontotext.semantic.impl.parser;

import static com.ontotext.semantic.core.common.SemanticInstanceUtil.parseToString;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.COMMA;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SQUARE_BRACE_CLOSE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SQUARE_BRACE_OPEN;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.parser.InstanceParser;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Instance> fromString(List<String> instances) {
		// TODO Auto-generated method stub
		return null;
	}
}
