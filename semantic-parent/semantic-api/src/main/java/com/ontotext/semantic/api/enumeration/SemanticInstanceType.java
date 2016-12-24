package com.ontotext.semantic.api.enumeration;

/**
 * Represents all basic instance types. Using current implementation a property is considered an instance
 * 
 * @author Svetlozar
 */
public enum SemanticInstanceType {

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
