package com.ontotext.semantic.core.api;

import java.util.ArrayList;
import java.util.Map;

import org.openrdf.model.Value;

public interface Instance {

	public InstanceType getInstanceType();

	public <T extends Value> T getInstanceValue();

	public Map<Instance, ArrayList<Instance>> getPropertyMap();

	public void insertProperty(Instance property, Instance value);

	public void removeProperty(Instance property);

}
