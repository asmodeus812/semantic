package com.ontotext.semantic.api.query;

import java.util.Map;
import java.util.Set;

import org.openrdf.model.Value;
import org.openrdf.query.impl.BindingImpl;

import com.ontotext.semantic.api.instance.Instance;

/**
 * Interface representing the base semantic query functionality
 * 
 * @author Svetlozar
 */
public interface SemanticQuery {

	/**
	 * Returns the query as a string
	 * 
	 * @return the query as a string
	 */
	public String getQuery();

	/**
	 * Returns all query parameters as annotated in the given query language used
	 * 
	 * @return the set of parameters
	 */
	public Set<String> getParameterSet();

	/**
	 * Returns all query parameters which have been mapped with a value
	 * 
	 * @return the parameter map
	 */
	public Map<String, BindingImpl> getParameterMap();

	/**
	 * Binds a value to a given parameter
	 * 
	 * @param parameter
	 *            the parameter
	 * @param binding
	 *            the value to be bound to the given parameter
	 */
	public void bind(String parameter, Value binding);

	/**
	 * Binds an instance to a given parameter
	 * 
	 * @param parameter
	 *            the parameter
	 * @param binding
	 *            the instance to be bound to the given parameter
	 */
	public void bind(String parameter, Instance binding);

	/**
	 * Un binds a value for a given parameter
	 * 
	 * @param parameter
	 *            the parameter should be unbinded
	 */
	public void unbind(String parameter);
}
