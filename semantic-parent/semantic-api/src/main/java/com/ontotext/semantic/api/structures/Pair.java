package com.ontotext.semantic.api.structures;

import java.io.Serializable;

/**
 * Interface representing a base pair structure. Extending single structures
 * 
 * @author Svetlozar
 */
public interface Pair extends Single {

	/**
	 * Get the second part of the pair
	 * 
	 * @return the second part
	 */
	public Serializable getY();

}
