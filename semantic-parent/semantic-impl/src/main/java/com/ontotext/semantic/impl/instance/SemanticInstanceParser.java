package com.ontotext.semantic.impl.instance;

import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SQUARE_BRACE_CLOSE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SQUARE_BRACE_OPEN;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.instance.InstanceParser;
import com.ontotext.semantic.core.common.SemanticInstanceUtil;

/**
 * Default implementation for the instance parser interface
 * 
 * @author Svetlozar
 */
public class SemanticInstanceParser implements InstanceParser {

	private static final int CAPACITY = 512;

	private JsonParser parser = new JsonParser();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public String toString(Instance instance) {
		StringBuilder stringBuilder = new StringBuilder(CAPACITY);
		SemanticInstanceUtil.parseToString(instance, stringBuilder);

		JsonObject json = parser.parse(stringBuilder.toString()).getAsJsonObject();
		return gson.toJson(json);
	}

	@Override
	public String toString(List<Instance> instances) {
		StringBuilder stringBuilder = new StringBuilder(CAPACITY * instances.size());
		stringBuilder.append(SQUARE_BRACE_OPEN);
		for (Instance instance : instances) {
			SemanticInstanceUtil.parseToString(instance, stringBuilder);
		}
		stringBuilder.append(SQUARE_BRACE_CLOSE);

		JsonArray json = parser.parse(stringBuilder.toString()).getAsJsonArray();
		return gson.toJson(json);
	}

	@Override
	public Instance fromString(String instance) {
		// TODO Auto-generated method stub
		return null;
	}
}
