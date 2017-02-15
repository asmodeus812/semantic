package com.ontotext.semantic.impl.query;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.openrdf.model.Value;
import org.openrdf.query.impl.BindingImpl;

import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.query.SemanticQuery;
import com.ontotext.semantic.core.common.SemanticQueryUtil;

/**
 * Semantic base query encapsulates all common functionalities of a semantic query
 * 
 * @author Svetlozar
 */
public class SemanticBaseQuery implements SemanticQuery {

	private String query;
	private Set<String> parameterSet = new TreeSet<String>();
	private Map<String, BindingImpl> parameterMap = new HashMap<String, BindingImpl>();

	/**
	 * Initializes a semantic base query
	 * 
	 * @param query
	 *            the query
	 */
	public SemanticBaseQuery(String query) {
		this.query = query;
		constructBaseQuery();
	}

	@Override
	public void bindV(String parameter, Value binding) {
		if (binding != null && parameter != null) {
			parameterMap.put(parameter, new BindingImpl(parameter, binding));
		}
	}

	@Override
	public void bindI(String parameter, Instance binding) {
		bindV(parameter, binding.getInstanceValue());
	}

	@Override
	public void unbind(String parameter) {
		if (parameterMap.containsKey(parameter)) {
			parameterMap.remove(parameter);
			parameterMap.put(parameter, null);
		}
	}

	@Override
	public String getQuery() {
		return query;
	}

	@Override
	public Set<String> getParameterSet() {
		return parameterSet;
	}

	@Override
	public Map<String, BindingImpl> getParameterMap() {
		return parameterMap;
	}

	/**
	 * Sets the underlying query
	 * 
	 * @param query
	 *            the new query
	 */
	protected void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Sets the underlying parameterSet
	 * 
	 * @param parameterSet
	 *            the new parameterSet
	 */
	protected void setParameterSet(Set<String> parameterSet) {
		this.parameterSet = parameterSet;
	}

	/**
	 * Sets the underlying parameterMap
	 * 
	 * @param parameterMap
	 *            the new parameterMap
	 */
	protected void setParamaterMap(Map<String, BindingImpl> parameterMap) {
		this.parameterMap = parameterMap;
	}

	private void constructBaseQuery() {
		parameterSet = SemanticQueryUtil.extractQueryParams(this.query);
	}

}
