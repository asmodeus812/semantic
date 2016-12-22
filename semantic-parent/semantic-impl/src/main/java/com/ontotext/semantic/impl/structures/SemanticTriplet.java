package com.ontotext.semantic.impl.structures;

import java.io.Serializable;

import com.ontotext.semantic.api.structures.Triplet;
import com.ontotext.semantic.core.common.SemanticSearchUtil;

/**
 * Represents a simple semantic triplet that can contain a various elements such as Strings, URI, Value, BNode, Literal
 * etc
 * 
 * @author Svetlozar
 */
public class SemanticTriplet implements Triplet {

	private static final long serialVersionUID = 1L;

	private Serializable first;
	private Serializable second;
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
		this.first = first;
		this.second = second;
		this.third = third;
	}

	@Override
	public Serializable getSecond() {
		return second;
	}

	@Override
	public Serializable getFirst() {
		return first;
	}

	@Override
	public Serializable getThird() {
		return third;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(256);

		sb.append(SemanticSearchUtil.SINGLE_SPACE);
		sb.append(getFirst()).append(SemanticSearchUtil.SINGLE_SPACE);
		sb.append(getSecond()).append(SemanticSearchUtil.SINGLE_SPACE);
		sb.append(getThird());

		return sb.toString();
	}
}
