package com.ontotext.semantic.impl.structures;

import java.io.Serializable;

import com.ontotext.semantic.api.structures.Pair;
import com.ontotext.semantic.core.common.SemanticSparqlUtil;

/**
 * Class containing a pair values of some serializable type
 * 
 * @author Svetlozar
 */
public class SemanticPair extends SemanticSingle implements Pair {

	private static final long serialVersionUID = 1L;

	private Serializable second;

	/**
	 * Initializes the semantic pair value class
	 * 
	 * @param value
	 *            the first value
	 * @param second
	 *            the second value
	 */
	public SemanticPair(Serializable value, Serializable second) {
		super(value);
		this.second = second;
	}

	@Override
	public Serializable getY() {
		return second;
	}

	@Override
	public String toString() {
		return super.toString() + SemanticSparqlUtil.SINGLE_SPACE + getY().toString();
	}
}
