package com.ontotext.semantic.impl.instance;

import static com.ontotext.semantic.impl.common.SemanticPrebuiltQuery.VALUE;
import static com.ontotext.semantic.impl.common.SemanticPrebuiltQuery.buildSemanticSelectQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openrdf.model.Value;
import org.openrdf.repository.RepositoryConnection;

import com.ontotext.semantic.api.enumeration.SemanticInstanceType;
import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.instance.InstanceChain;
import com.ontotext.semantic.api.parser.SemanticQueryParser;
import com.ontotext.semantic.api.query.SemanticTupleQuery;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;
import com.ontotext.semantic.impl.parser.SemanticTupleQueryParser;
import com.ontotext.semantic.impl.query.SemanticSelectQuery;

/**
 * Default Class implementation for a semantic resource chaining
 * 
 * @author Svetlozar
 */
public class SemanticInstanceChain implements InstanceChain {

	private int maximumDepthLevel = DEFAULT_DEPTH_LEVEL;

	private SemanticTupleQuery query;
	private QueryCompiler selectCompiler;
	private RepositoryConnection connection;

	private Map<Serializable, Instance> processed = new HashMap<>();
	private SemanticQueryParser<SemanticTupleQuery> parser = new SemanticTupleQueryParser();

	/**
	 * Initialize a semantic instance resource chain with a given repository connection
	 * 
	 * @param connection
	 *            the repository connection
	 */
	public SemanticInstanceChain(RepositoryConnection connection) {
		this.connection = connection;
		this.selectCompiler = buildSemanticSelectQuery();
		this.query = new SemanticSelectQuery(selectCompiler.longFormatQuery());
	}

	@Override
	public void setMaxDepthLevel(int maximumDepthLevel) {
		this.maximumDepthLevel = maximumDepthLevel;
	}

	@Override
	public void unwrap(Instance instance) {
		// Unwraps the instance in place by reference
		Set<Instance> visited = new HashSet<>();
		instanceUnwrap(instance, visited, 0);
	}

	@Override
	public void unwrap(List<Instance> instances) {
		// Unwraps all instances in the given list
		for (int i = 0; i < instances.size(); i++) {
			unwrap(instances.get(i));
		}
	}

	/**
	 * Unwraps an instance and all of it's resource properties
	 * 
	 * @param instance
	 *            the instance to be unwrapped
	 * @param visited
	 *            the current visited resources
	 * @param level
	 *            the current depth of the unwrapping
	 */
	private void instanceUnwrap(Instance instance, Set<Instance> visited, int level) {
		// Get the properties map value from the current instance
		Map<Instance, ArrayList<Instance>> properties = instance.getPropertyMap();

		// Extract all properties and value pairs from the instance property map
		for (Map.Entry<Instance, ArrayList<Instance>> pair : properties.entrySet()) {
			List<Instance> result = new ArrayList<Instance>();

			// Extract all values for a given property
			List<Instance> instances = pair.getValue();

			// For each property extract the list of values
			for (Instance value : instances) {
				if (value.getInstanceType() == SemanticInstanceType.INSTANCE && !visited.contains(value)
						&& level < maximumDepthLevel) {
					visited.add(value);

					// Extract all tuples and parse them
					query.bindI(VALUE, value);
					List<Instance> current = parser.parseQuery(connection, query);
					// Loop over the parsed results and unwrap the instance
					for (int i = 0; i < current.size(); i++) {
						Value currrentVal = current.get(i).getInstanceValue();
						if (processed.containsKey(currrentVal)) {
							// Get processed unwrapped instances from the global map
							current.set(i, processed.get(currrentVal));
						} else {
							// Unwrap instance and put it back in the global map for reuse
							instanceUnwrap(current.get(i), visited, level + 1);
							processed.put(currrentVal, current.get(i));
						}
						result.add(current.get(i));
					}
					visited.remove(value);
				}
			}
			// If any results are found replace the old values with the unwrapped instances
			if (!result.isEmpty()) {
				properties.put(pair.getKey(), (ArrayList<Instance>) result);
			}
		}
	}
}
