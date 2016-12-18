package com.ontotext.semantic.api.instance;

/**
 * Represents all basic instance types. Using current implementation a property is considered an instance
 * 
 * @author Svetlozar
 */
public enum InstanceType {

	/**
	 * Represents a semantic instance or a property
	 */
	INSTANCE,

	/**
	 * Represents a base atomic or a literal type
	 */
	LITERAL,

	/**
	 * Represents a BNode graph structure
	 */
	BNODE
}
