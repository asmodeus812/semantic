package com.ontotext.semantic.api.structures;

import java.io.Serializable;

/**
 * Interface representing a base single structure.
 * 
 * @author Svetlozar
 */
public interface Single extends Serializable {

	/**
	 * Get the first value of the single structure
	 * 
	 * @return the first value
	 */
	public Serializable getX();
}
