package com.ontotext.semantic.impl.structures;

import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SINGLE_SPACE;

import java.io.Serializable;

import com.ontotext.semantic.api.structures.Triplet;

/**
 * Represents a simple semantic triplet that can contain a various elements such as Strings, URI, Value, BNode, Literal
 * etc
 * 
 * @author Svetlozar
 */
public class SemanticTriplet extends SemanticPair implements Triplet {

	private static final long serialVersionUID = 1L;

	private Serializable third;

	/**
	 * Initializes the semantic triplet
	 * 
	 * @param first
	 *            the first part of the triplet
	 * @param second
	 *            the second part of the triplet
	 * @param third
	 *            the third part of the triplet
	 */
	public SemanticTriplet(Serializable first, Serializable second, Serializable third) {
		super(first, second);
		this.third = third;
	}

	@Override
	public Serializable getZ() {
		return third;
	}

	@Override
	public String toString() {
		return super.toString() + SINGLE_SPACE + getZ().toString();
	}
}
