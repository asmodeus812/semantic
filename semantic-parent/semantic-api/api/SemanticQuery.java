package com.ontotext.semantic.core.api;

import java.util.Map;
import java.util.Set;

import org.openrdf.model.Value;
import org.openrdf.query.impl.BindingImpl;

public interface SemanticQuery {

	public String getQuery();

	public Set<String> getParameterSet();

	public Map<String, BindingImpl> getParameterMap();

	public void bind(String parameter, Value binding);

	public void unbind(String parameter);
}
