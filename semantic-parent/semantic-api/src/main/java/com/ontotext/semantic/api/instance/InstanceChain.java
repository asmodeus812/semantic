package com.ontotext.semantic.api.instance;

import java.util.List;

/**
 * Interface used to chain and unwrap instances based on their properties
 * 
 * @author Svetlozar
 */
public interface InstanceChain {

	/**
	 * Unwraps a given instance and all of it's properties and values. Unwrapping is in place using the instance
	 * reference
	 * 
	 * @param instance
	 *            the instance to be unwrapped
	 */
	void unwrap(Instance instance);

	/**
	 * Unwraps a given instances and all of it's properties and values. Unwrapping is in place using the instance
	 * 
	 * @param instances
	 *            the instances to be unwrapped
	 */
	void unwrap(List<Instance> instances);
}
