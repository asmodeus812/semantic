package com.ontotext.semantic.impl.instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openrdf.model.URI;
import org.openrdf.model.Value;

import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.instance.InstanceType;
import com.ontotext.semantic.core.common.SemanticSparqlUtil;

/**
 * Represents a semantic instance, could be an actual instance or a semantic property
 * 
 * @author Svetlozar
 */
public class SemanticInstance implements Instance {

	private URI instanceValue;
	private Map<Instance, ArrayList<Instance>> propertiesMap = new HashMap<Instance, ArrayList<Instance>>();

	/**
	 * Initializes a semantic instance from a given value
	 * 
	 * @param value
	 *            the value from which the instance should be initialized
	 */
	public SemanticInstance(Value value) {
		this.instanceValue = (URI) value;
	}

	@Override
	@SuppressWarnings("unchecked")
	public URI getInstanceValue() {
		return instanceValue;
	}

	@Override
	public Map<Instance, ArrayList<Instance>> getPropertyMap() {
		return propertiesMap;
	}

	@Override
	public void insertProperty(Instance property, Instance value) {
		if (!propertiesMap.containsKey(property)) {
			propertiesMap.put(property, new ArrayList<Instance>());
		}
		propertiesMap.get(property).add(value);
	}

	@Override
	public void removeProperty(Instance property) {
		propertiesMap.remove(property);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(2048);

		// Builds the instance as a JSON like string
		builder.append(getInstanceValue().getLocalName());
		if (propertiesMap.size() != 0) {
			builder.append(SemanticSparqlUtil.COLLON);
			builder.append(SemanticSparqlUtil.CURLY_BRACE_OPEN).append(System.lineSeparator());
			for (Map.Entry<Instance, ArrayList<Instance>> entry : propertiesMap.entrySet()) {
				String firstHalf = (entry.getKey() != null) ? entry.getKey().toString()
						: SemanticSparqlUtil.EMPTY_STRING;
				String secondHalf = (entry.getValue() != null)
						? SemanticSparqlUtil.COLLON + SemanticSparqlUtil.SINGLE_SPACE + entry.getValue()
						: SemanticSparqlUtil.EMPTY_STRING;
				if (!firstHalf.equals(SemanticSparqlUtil.EMPTY_STRING)) {
					builder.append(firstHalf);
				}
				if (!secondHalf.equals(SemanticSparqlUtil.EMPTY_STRING)) {
					builder.append(secondHalf);
				}
				builder.append(System.lineSeparator());
			}
			builder.append(SemanticSparqlUtil.CURLY_BRACE_CLOSE.trim());
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return instanceValue.getLocalName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof SemanticInstance)) {
			return false;
		}

		SemanticInstance other = (SemanticInstance) obj;
		return instanceValue.getLocalName().equals(other.instanceValue.getLocalName());
	}

	@Override
	public InstanceType getInstanceType() {
		return InstanceType.INSTANCE;
	}

}
