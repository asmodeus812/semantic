package com.ontotext.semantic.api.structures;

import java.io.Serializable;

/**
 * Interface representing a base triplet structure. Extending pair and single structures
 * 
 * @author Svetlozar
 */
public interface Triplet extends Pair {

	/**
	 * Get the third part of the triplet
	 * 
	 * @return the third part
	 */
	Serializable getThird();
}
