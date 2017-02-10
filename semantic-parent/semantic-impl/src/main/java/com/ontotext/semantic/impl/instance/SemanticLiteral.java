package com.ontotext.semantic.impl.instance;

import java.util.ArrayList;
import java.util.Map;

import org.openrdf.model.Literal;
import org.openrdf.model.Value;

import com.ontotext.semantic.api.enumeration.SemanticInstanceType;
import com.ontotext.semantic.api.instance.Instance;

/**
 * Represents a semantic literal or atomic value that can not be sub classed
 * 
 * @author Svetlozar
 */
public class SemanticLiteral implements Instance {

	private Literal literal;

	/**
	 * Initializes a semantic atomic from a given value
	 * 
	 * @param atomicValue
	 *            the atomic value or a literal
	 */
	public SemanticLiteral(Value atomicValue) {
		this.literal = (Literal) atomicValue;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Literal getInstanceValue() {
		return literal;
	}

	@Override
	public Map<Instance, ArrayList<Instance>> getPropertyMap() {
		// A literal can not have any properties
		return null;
	}

	@Override
	public void insertProperty(Instance property, Instance value) {
		// No properties can be inserted for a literal
	}

	@Override
	public void removeProperty(Instance property) {
		// No properties can be removed for a literal
	}

	@Override
	public String toString() {
		return literal.getLabel();
	}

	@Override
	public int hashCode() {
		return literal.getLabel().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof SemanticLiteral)) {
			return false;
		}

		SemanticLiteral other = (SemanticLiteral) obj;
		return literal.getLabel().equals(other.literal.getLabel());
	}

	@Override
	public SemanticInstanceType getInstanceType() {
		return SemanticInstanceType.LITERAL;
	}
}
