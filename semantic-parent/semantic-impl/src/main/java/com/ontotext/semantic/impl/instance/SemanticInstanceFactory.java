package com.ontotext.semantic.impl.instance;

import org.openrdf.model.BNode;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.instance.InstanceFactory;

/**
 * Simple semantic instance factory creating instances based on a given value
 * 
 * @author Svetlozar
 */
public class SemanticInstanceFactory implements InstanceFactory {

	@Override
	public Instance createInstance(Value value) {
		if (value instanceof URI) {
			return new SemanticInstance(value);
		} else if (value instanceof BNode) {
			return new SemanticBNode(value);
		} else if (value instanceof Literal) {
			return new SemanticAtomic(value);
		} else {
			return null;
		}
	}

}
