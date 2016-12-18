package com.ontotext.semantic.api.instance;

import org.openrdf.model.Value;

/**
 * Represents an Instance Factory
 * 
 * @author Svetlozar
 */
public interface InstanceFactory {

	/**
	 * Creates an instance from a given {@link Value}
	 * 
	 * @param value
	 *            the value from which the instance should be constructed
	 * @return the built instance
	 */
	public Instance createInstance(Value value);
}
