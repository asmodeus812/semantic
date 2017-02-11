package com.ontotext.semantic.api.persist;

import java.util.List;

/**
 * Semantic persister interface used to persist multiple types
 * 
 * @param <T>
 * @author Svetlozar
 */
public interface SemanticPersister<T> {

	/**
	 * Persists the given object
	 * 
	 * @param toPersist
	 *            the object to persist
	 */
	public void persist(T toPersist);

	/**
	 * Persists the given list of objects
	 * 
	 * @param toPersist
	 *            the list of objects to persist
	 */
	public void persist(List<T> toPersist);

	/**
	 * Removes the given object
	 * 
	 * @param toRemove
	 *            the object to be removed
	 */
	public void remove(T toRemove);

	/**
	 * Removes the given list of objects
	 * 
	 * @param toRemove
	 *            the list of objects to be removed
	 */
	public void remove(List<T> toRemove);

	/**
	 * Updates the given object
	 * 
	 * @param toRemove
	 *            the object to be updated
	 */
	public void update(T toUpdate);

}
