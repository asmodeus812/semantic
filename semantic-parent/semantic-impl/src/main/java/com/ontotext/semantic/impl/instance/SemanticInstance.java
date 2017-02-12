package com.ontotext.semantic.impl.instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openrdf.model.URI;
import org.openrdf.model.Value;

import com.ontotext.semantic.api.common.Converter;
import com.ontotext.semantic.api.enumeration.SemanticInstanceType;
import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.impl.converter.LongUriConverter;
import com.ontotext.semantic.impl.converter.NullUriConverter;
import com.ontotext.semantic.impl.converter.ShortUriConverter;

/**
 * Represents a semantic instance, could be an actual instance or a semantic property
 * 
 * @author Svetlozar
 */
public class SemanticInstance implements Instance {

	/**
	 * Complete URI chain converter supporting all basic types - short and long URIs
	 */
	public static final Converter<URI> URI_CONVERTER = new ShortUriConverter(
			new LongUriConverter(new NullUriConverter()));

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

	/**
	 * Initializes a semantic instance from a given string value - short or long URI
	 * 
	 * @param value
	 *            the value from which the instance should be initialized
	 */
	public SemanticInstance(String value) {
		this.instanceValue = URI_CONVERTER.convert(value);
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

		if (value != null) {
			propertiesMap.get(property).add(value);
		}
	}

	@Override
	public void modifyProperty(Instance property, Instance oldValue, Instance newValue) {
		if (propertiesMap.containsKey(property)) {
			List<Instance> values = propertiesMap.get(property);
			for (int i = 0; i < values.size(); i++) {
				Instance instance = values.get(i);
				if (instance.equals(oldValue)) {
					values.set(i, newValue);
					return;
				}
			}
		}
	}

	@Override
	public void removeProperty(Instance property) {
		propertiesMap.remove(property);
	}

	@Override
	public String toString() {
		return instanceValue.getLocalName();
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
	public SemanticInstanceType getInstanceType() {
		return SemanticInstanceType.INSTANCE;
	}

}
