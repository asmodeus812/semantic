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
public class SemanticAtomic implements Instance {

	private Literal atomicLiteral;

	/**
	 * Initializes a semantic atomic from a given value
	 * 
	 * @param atomicValue
	 *            the atomic value or a literal
	 */
	public SemanticAtomic(Value atomicValue) {
		this.atomicLiteral = (Literal) atomicValue;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Literal getInstanceValue() {
		return atomicLiteral;
	}

	@Override
	public Map<Instance, ArrayList<Instance>> getPropertyMap() {
		return null;
	}

	@Override
	public void insertProperty(Instance property, Instance value) {
	}

	@Override
	public void removeProperty(Instance property) {
	}

	@Override
	public String toString() {
		return atomicLiteral.getLabel();
	}

	@Override
	public int hashCode() {
		return atomicLiteral.getLabel().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof SemanticAtomic)) {
			return false;
		}

		SemanticAtomic other = (SemanticAtomic) obj;
		return atomicLiteral.getLabel().equals(other.atomicLiteral.getLabel());
	}

	@Override
	public SemanticInstanceType getInstanceType() {
		return SemanticInstanceType.LITERAL;
	}

}
