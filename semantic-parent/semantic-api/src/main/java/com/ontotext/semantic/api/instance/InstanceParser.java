package com.ontotext.semantic.api.instance;

import java.util.List;

/**
 * Interface serving as a instance parser to convert from and to instances from or to any given source
 * 
 * @author Svetlozar
 */
public interface InstanceParser {

	/**
	 * Converts an instance to a JSON like string representation
	 * 
	 * @param instance
	 *            the instance to be converted
	 * @return the string representation
	 */
	public String toString(Instance instance);

	/**
	 * Converts list of instances to a JSON like string representation
	 * 
	 * @param instances
	 *            the list of instances to be converted
	 * @return the string representation of the instances
	 */
	public String toString(List<Instance> instances);

	/**
	 * Converts a JSON like string to an instance
	 * 
	 * @param instance
	 *            the instance JSON string
	 * @return the built instance
	 */
	public Instance fromString(String instance);
}
