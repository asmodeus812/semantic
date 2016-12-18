package com.ontotext.semantic.impl.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;

import com.ontotext.semantic.api.exception.SemanticParseException;
import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.instance.InstanceFactory;
import com.ontotext.semantic.api.query.SemanticQueryParser;
import com.ontotext.semantic.api.query.SemanticTupleQuery;
import com.ontotext.semantic.impl.instance.SemanticInstanceFactory;

/**
 * Base query parser of a semantic tuple query
 * 
 * @author Svetlozar
 */
public class SemanticTupleQueryParser implements SemanticQueryParser<SemanticTupleQuery> {

	@Override
	public List<Instance> parseQuery(RepositoryConnection connection, SemanticTupleQuery query) {
		try {
			TupleQueryResult result = query.evaluate(connection);
			List<String> parameters = new ArrayList<String>(query.getParameterSet());
			// Always have a triplet as a maximum numbers of parameters ?
			if (parameters.size() < 1 && parameters.size() > 3) {
				return null;
			}

			InstanceFactory factory = new SemanticInstanceFactory();
			Map<Value, Instance> instanceMap = new HashMap<Value, Instance>();
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();

				// Extract the URI for the subject
				Value instanceVal = bindingSet.getValue(parameters.get(0));
				if (!instanceMap.containsKey(instanceVal)) {
					instanceMap.put(instanceVal, factory.createInstance(instanceVal));
				}

				// Extract the property if available
				if (parameters.size() > 1) {
					Instance instance = instanceMap.get(instanceVal);
					Value predicate = bindingSet.getValue(parameters.get(1));
					Instance property = factory.createInstance(predicate);
					Instance value = null;

					// Extract property's value if available
					if (parameters.size() > 2) {
						Value object = bindingSet.getValue(parameters.get(2));
						value = factory.createInstance(object);
					}
					instance.insertProperty(property, value);
				}
			}
			result.close();
			return new ArrayList<Instance>(instanceMap.values());
		} catch (QueryEvaluationException e) {
			throw new SemanticParseException("Error during query parsing" + e.getMessage());
		}

	}

}
