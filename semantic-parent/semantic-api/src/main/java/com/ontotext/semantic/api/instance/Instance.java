package com.ontotext.semantic.api.instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openrdf.model.Value;

import com.ontotext.semantic.api.enumeration.SemanticInstanceType;

/**
 * Represents a base semantic instance
 * 
 * @author Svetlozar
 */
public interface Instance {

	/**
	 * Returns the type of the instance
	 * 
	 * @return the instance type
	 */
	public SemanticInstanceType getInstanceType();

	/**
	 * Returns instance value as specified by the {@link Value} interface
	 * 
	 * @return the value of the instance
	 */
	public <T extends Value> T getInstanceValue();

	/**
	 * Returns a property map of the given instance
	 * 
	 * @return the property map of the instance
	 */
	public Map<Instance, ArrayList<Instance>> getPropertyMap();

	/**
	 * Inserts a property inside the property map
	 * 
	 * @param property
	 *            the property to be inserted
	 * @param value
	 *            the value of the property
	 */
	public void insertProperty(Instance property, Instance value);

	/**
	 * Inserts a property inside the property map
	 * 
	 * @param property
	 *            the property to be inserted
	 * @param value
	 *            the list of values to be inserted for the property
	 */
	public void insertProperty(Instance property, List<Instance> value);

	/**
	 * Modifies a property inside the property map
	 * 
	 * @param property
	 *            the property to be inserted
	 * @param oldValue
	 *            the old value of the property
	 * @param newValue
	 *            the new value of the property
	 */
	public void modifyProperty(Instance property, Instance oldValue, Instance newValue);

	/**
	 * Removes a property from the property map
	 * 
	 * @param property
	 *            the property to be removed
	 */
	public void removeProperty(Instance property);

}
