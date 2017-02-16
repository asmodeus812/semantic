package com.ontotext.semantic.impl.instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openrdf.model.BNode;
import org.openrdf.model.Value;

import com.ontotext.semantic.api.enumeration.SemanticInstanceType;
import com.ontotext.semantic.api.instance.Instance;

/**
 * Represents a semantic BNode structure
 * 
 * @author Svetlozar
 */
public class SemanticBNode implements Instance {

	private BNode bnodeValue;

	/**
	 * Initializes a semantic BNode from a given value
	 * 
	 * @param bnodeValue
	 *            the BNode value
	 */
	public SemanticBNode(Value bnodeValue) {
		this.bnodeValue = (BNode) bnodeValue;
	}

	@Override
	public SemanticInstanceType getInstanceType() {
		return SemanticInstanceType.BNODE;
	}

	@Override
	@SuppressWarnings("unchecked")
	public BNode getInstanceValue() {
		return bnodeValue;
	}

	@Override
	public Map<Instance, ArrayList<Instance>> getPropertyMap() {
		return null;
	}

	@Override
	public void modifyProperty(Instance property, Instance newValue) {
		// TODO Auto-generated method stub
	}

	@Override
	public void modifyProperty(Instance property, Instance oldValue, Instance newValue) {
		// TODO Auto-generated method stub
	}

	@Override
	public void insertProperty(Instance property, Instance value) {
		// TODO Auto-generated method stub
	}

	@Override
	public void insertProperty(Instance property, List<Instance> value) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeProperty(Instance property) {
		// TODO Auto-generated method stub
	}

	@Override
	public String toString() {
		return bnodeValue.getID();
	}

	@Override
	public int hashCode() {
		return bnodeValue.getID().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof SemanticBNode)) {
			return false;
		}

		SemanticBNode other = (SemanticBNode) obj;
		return bnodeValue.getID().equals(other.bnodeValue.getID());
	}

}
