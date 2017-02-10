package com.ontotext.semantic.impl.instance;

import static com.ontotext.semantic.core.common.SemanticSparqlUtil.OBJECT;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.PREDICATE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SUBJECT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.RepositoryConnection;

import com.ontotext.semantic.api.enumeration.ArithmeticOperators;
import com.ontotext.semantic.api.enumeration.SemanticInstanceType;
import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.instance.InstanceChain;
import com.ontotext.semantic.api.query.SemanticTupleQuery;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;
import com.ontotext.semantic.api.query.parser.SemanticQueryParser;
import com.ontotext.semantic.impl.query.SemanticSelectQuery;
import com.ontotext.semantic.impl.query.SemanticTupleQueryParser;
import com.ontotext.semantic.impl.query.builders.SemanticQueryBuilder;

/**
 * Base Class implementation for a semantic resource chaining
 * 
 * @author Svetlozar
 */
public class SemanticInstanceChain implements InstanceChain {

	/**
	 * Value variable name for which to bind when searching for a given instance
	 */
	private static final String VALUE = "value";

	/**
	 * All instances of the given class will be considered for unwrapping
	 */
	private static final String CLASS = "framework:class";

	/**
	 * All properties of the given type will be included in the instance
	 */
	private static final String PROPERTY = "framework:property";

	private SemanticTupleQuery query;
	private QueryCompiler selectCompiler;
	private RepositoryConnection connection;

	private Map<Serializable, Instance> processed = new HashMap<>();
	private SemanticQueryParser<SemanticTupleQuery> parser = new SemanticTupleQueryParser();

	/**
	 * Initialize a semantic instance chain with a given repository connection
	 * 
	 * @param connection
	 *            the repository connection
	 */
	public SemanticInstanceChain(RepositoryConnection connection) {
		this.connection = connection;
		this.selectCompiler = selectInstanceQuery();
		this.query = new SemanticSelectQuery(selectCompiler.longFormatQuery());
	}

	@Override
	public void unwrap(Instance instance) {
		// Unwraps the instance in place by reference
		Set<Instance> visited = new HashSet<>();
		visited.add(instance);
		instanceUnwrap(instance, visited);
	}

	@Override
	public void unwrap(List<Instance> instances) {
		// Unwraps all instances in the given list
		for (int i = 0; i < instances.size(); i++) {
			unwrap(instances.get(i));
		}
	}

	/**
	 * Un wraps an instance and all of it's resource properties
	 * 
	 * @param instance
	 *            the instance to be un wrapped
	 * @param visited
	 *            the current visited resources
	 */
	private void instanceUnwrap(Instance instance, Set<Instance> visited) {
		// Get the properties map value from the current instance
		Map<Instance, ArrayList<Instance>> properties = instance.getPropertyMap();

		// Extract all properties and value pairs from the instance property map
		for (Map.Entry<Instance, ArrayList<Instance>> pair : properties.entrySet()) {
			List<Instance> result = new ArrayList<Instance>();

			// Extract all values for a given property
			List<Instance> instances = pair.getValue();

			// For each property extract the list of values
			for (Instance value : instances) {
				if (value.getInstanceType() == SemanticInstanceType.INSTANCE
						&& !visited.contains(value)) {
					visited.add(value);
					// Get processed unwrapped instances from the global map
					Serializable instanceValue = value.getInstanceValue();
					if (processed.containsKey(instanceValue)) {
						Instance unwrapped = processed.get(instanceValue);
						result.add(unwrapped);
					}
					// Unwrap instance and put it back in the global map for reuse
					else {
						query.bind(VALUE, value.getInstanceValue());
						List<Instance> current = parser.parseQuery(connection, query);
						for (int i = 0; i < current.size(); i++) {
							instanceUnwrap(current.get(i), visited);
							processed.put(current.get(i).getInstanceValue(), current.get(i));
						}
						result.addAll(current);
					}
				}
			}
			// If any results are found replace the old values with the unwrapped instances
			if (!result.isEmpty()) {
				properties.put(pair.getKey(), (ArrayList<Instance>) result);
			}
		}
	}

	/**
	 * Prepares a select query which selects all instances which are of type framework:class and have properties of type
	 * framework:property
	 * 
	 * @return the query compiled prepared query
	 */
	private QueryCompiler selectInstanceQuery() {
		return new SemanticQueryBuilder(SemanticQueryType.SELECT_DISTINCT)
				.appendStatement(SUBJECT, PREDICATE, OBJECT)
				.appendCondition(SUBJECT, PREDICATE, OBJECT)
				.appendCondition(SUBJECT, RDF.TYPE, "?f")
				.appendCondition("?f", RDF.TYPE, CLASS)
				.appendCondition(PREDICATE, RDF.TYPE, "?m")
				.appendCondition("?m", RDF.TYPE, PROPERTY)
				.appendFilter(SUBJECT, ArithmeticOperators.EQUALS, "?value")
				.compile();
	}

}
