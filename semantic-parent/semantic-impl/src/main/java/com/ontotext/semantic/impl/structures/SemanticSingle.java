package com.ontotext.semantic.impl.structures;

import java.io.Serializable;

import com.ontotext.semantic.api.structures.Single;

/**
 * Class containing a single value of some serializable type
 * 
 * @author Svetlozar
 */
public class SemanticSingle implements Single {

	private static final long serialVersionUID = 1L;

	private Serializable first;

	/**
	 * Initializes the semantic single value class
	 * 
	 * @param value
	 *            the value to be stored
	 */
	public SemanticSingle(Serializable value) {
		this.first = value;
	}

	@Override
	public Serializable getX() {
		return first;
	}

	@Override
	public String toString() {
		return getX().toString();
	}
}
