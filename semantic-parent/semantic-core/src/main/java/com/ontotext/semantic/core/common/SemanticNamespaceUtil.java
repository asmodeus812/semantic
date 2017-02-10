package com.ontotext.semantic.core.common;

import static com.ontotext.semantic.core.common.SemanticSparqlUtil.ANGLE_BRACE_CLOSE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.ANGLE_BRACE_OPEN;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.ESCAPED_QUOTE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.TYPECAST;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openrdf.model.Literal;
import org.openrdf.model.Namespace;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.NamespaceImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.OWL;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 * Utility class offering utility methods for constructing and working with SPARQL name spaces and prefixes
 * 
 * @author Svetlozar
 */
public class SemanticNamespaceUtil {

	/**
	 * Default name space separator for SPARQL queries
	 */
	public static final String NAMESPACE_SEPARATOR = SemanticSparqlUtil.COLLON;

	/**
	 * Ontology name space
	 */
	public static final Namespace ONTOLOGY_NAMESPACE;

	/**
	 * Type name space
	 */
	public static final Namespace TYPE_NAMESPACE;

	/**
	 * Framework name space
	 */
	public static final Namespace FRAMEWORK_NAMESPACE;

	/**
	 * Class name space
	 */
	public static final Namespace CLASS_NAMESPACE;

	/**
	 * Attribute name space
	 */
	public static final Namespace ATTRIBUTE_NAMESPACE;

	/**
	 * Data name space
	 */
	public static final Namespace DATA_NAMESPACE;

	/**
	 * Mapping for all name spaced including all base and additional name spaces
	 */
	public static final Set<Namespace> NAMESPACE_MAPPING = new TreeSet<Namespace>();

	/**
	 * Private constructor for utility class
	 */
	private SemanticNamespaceUtil() {
	}

	/*
	 * Statically initialize the name space mapping
	 */
	static {
		// custom name spaces
		ONTOLOGY_NAMESPACE = new NamespaceImpl("ontology", "http://www.framework.com/ontology#");
		TYPE_NAMESPACE = new NamespaceImpl("type", "http://www.framework.com/type#");
		FRAMEWORK_NAMESPACE = new NamespaceImpl("framework", "http://www.framework.com/framework#");
		CLASS_NAMESPACE = new NamespaceImpl("class", "http://www.framework.com/class#");
		ATTRIBUTE_NAMESPACE = new NamespaceImpl("attribute", "http://www.framework.com/attribute#");
		DATA_NAMESPACE = new NamespaceImpl("data", "http://examples.ontotext.com/data#");

		// add all name spaces to the mapping
		NAMESPACE_MAPPING.add(OWL.NS);
		NAMESPACE_MAPPING.add(RDF.NS);
		NAMESPACE_MAPPING.add(RDFS.NS);
		NAMESPACE_MAPPING.add(XMLSchema.NS);

		// add all custom name spaces to the mapping
		NAMESPACE_MAPPING.add(DATA_NAMESPACE);
		NAMESPACE_MAPPING.add(TYPE_NAMESPACE);
		NAMESPACE_MAPPING.add(CLASS_NAMESPACE);
		NAMESPACE_MAPPING.add(ONTOLOGY_NAMESPACE);
		NAMESPACE_MAPPING.add(FRAMEWORK_NAMESPACE);
		NAMESPACE_MAPPING.add(ATTRIBUTE_NAMESPACE);
	}

	/**
	 * Builds a long URI model out of a string representation of a name space
	 * 
	 * @param instanceURI
	 *            the instance represented as either a long or a short URI
	 * @return the built URI object
	 */
	public static URI buildInstanceLongUri(String instanceURI) {
		String[] split = instanceURI.split(NAMESPACE_SEPARATOR);
		Namespace ns = findNamespace(split[0]);
		return new URIImpl(ns.getName() + split[1]);
	}

	/**
	 * Builds a long URI model out of a string representation of a literal
	 * 
	 * @param literal
	 *            the literal to be built into long URI model
	 * @return the converted literal
	 */
	public static Literal buildLiteralLongUri(String literal, URI dataType) {
		return new LiteralImpl(literal, dataType);
	}

	/**
	 * Matches all short & long URI contained inside the given string or a query and replaces them with complete long
	 * URI formats
	 * 
	 * @param sourceQuery
	 *            the source query or a string which is to be parsed
	 * @return the parsed query or a string containing all long URIs & instance local names preserved
	 */
	public static String parseToRawNamespace(String sourceQuery) {
		String longParsed = parseLongFormatNamespaces(sourceQuery);
		return parseShortFormatNamespace(longParsed);
	}

	/**
	 * Matches all short URI contained inside the given string or a query and replaces them with complete long URI
	 * formats
	 * 
	 * @param sourceQuery
	 *            the source query or a string which is to be parsed
	 * @return the parsed query or a string containing all complete long URIs & instance local names preserved
	 */
	public static String parseShortFormatNamespace(String sourceQuery) {
		Pattern prefixPattern = Pattern.compile("([a-zA-Z1-9]+:)([a-zA-Z1-9]+)");
		Matcher prefixMatcher = prefixPattern.matcher(sourceQuery);

		while (prefixMatcher.find()) {
			String prefix = prefixMatcher.group(1);
			String suffix = prefixMatcher.group(2);
			Namespace space = findNamespace(prefix);

			if (space != null) {
				URI uri = new URIImpl(space.getName() + suffix);
				String finalUri = convertValueForQuery(uri);

				sourceQuery = new StringBuilder(sourceQuery)
						.replace(prefixMatcher.start(1), prefixMatcher.end(2), finalUri).toString();
				prefixMatcher = prefixPattern.matcher(sourceQuery);
			}
		}
		return sourceQuery;
	}

	/**
	 * Matches all incomplete long URI contained inside the given string or a query and replaces them with complete long
	 * URI formats
	 * 
	 * @param sourceQuery
	 *            the source query or a string which is to be parsed
	 * @return the parsed query or a string containing all complete long URIs & instance local names preserved
	 */
	public static String parseLongFormatNamespaces(String sourceQuery) {
		Pattern longPattern = Pattern.compile("\\s(http:\\/\\/[\\w\\.\\/\\-\\d]+#[\\w\\d\\-]+)\\s");
		Matcher longMatcher = longPattern.matcher(sourceQuery);

		while (longMatcher.find()) {
			String longUri = longMatcher.group();
			URI uri = new URIImpl(longUri.trim());
			String finalUri = convertValueForQuery(uri);

			sourceQuery = new StringBuilder(sourceQuery).replace(longMatcher.start(1), longMatcher.end(1), finalUri)
					.toString();
			longMatcher = longPattern.matcher(sourceQuery);
		}
		return sourceQuery;
	}

	/**
	 * Finds the proper name space inside the set of all name spaces comparing by prefixes
	 * 
	 * @param prefix
	 *            the prefix based on which the name space will be located
	 * @return the found name space, or null if no name space was found with such prefix
	 */
	public static Namespace findNamespace(String prefix) {
		String matchPrefix = prefix.replace(NAMESPACE_SEPARATOR, SemanticSparqlUtil.EMPTY_STRING);
		Iterator<Namespace> it = NAMESPACE_MAPPING.iterator();
		while (it.hasNext()) {
			Namespace current = it.next();
			if (current.getPrefix().equals(matchPrefix)) {
				return current;
			}
		}
		return null;
	}

	/**
	 * Converts a given Value to a query prepared string. Value can be either an URI, BNode or a Literal
	 * 
	 * @param value
	 *            the Value to be converted
	 * @return the converted Value as a string or a null if unable to convert the value
	 */
	public static String convertValueForQuery(Value value) {
		if (value instanceof URI) {
			return ANGLE_BRACE_OPEN + value.stringValue() + ANGLE_BRACE_CLOSE;
		} else if (value instanceof Literal) {
			Literal literal = (Literal) value;
			String escapedValue = ESCAPED_QUOTE + literal.getLabel() + ESCAPED_QUOTE;
			String castType = TYPECAST + convertValueForQuery(literal.getDatatype());
			return escapedValue + castType;
		}
		return null;
	}
}
