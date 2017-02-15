package com.ontotext.semantic.impl.persist;

import static com.ontotext.semantic.impl.common.SemanticPrebuiltQuery.VALUE;
import static com.ontotext.semantic.impl.common.SemanticPrebuiltQuery.buildSemanticDeleteQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openrdf.model.URI;
import org.openrdf.repository.RepositoryConnection;

import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.persist.SemanticPersister;
import com.ontotext.semantic.api.query.SemanticUpdateQuery;
import com.ontotext.semantic.api.query.builders.QueryBuilder;
import com.ontotext.semantic.api.query.builders.QueryStatementBuilder;
import com.ontotext.semantic.impl.query.SemanticDataQuery;
import com.ontotext.semantic.impl.query.builders.SemanticQueryBuilder;

/**
 * Semantic persister implementation for semantic instances
 * 
 * @author Svetlozar
 */
public class SemanticInstancePersister implements SemanticPersister<Instance> {

	private RepositoryConnection connection;

	/**
	 * Initialize a semantic instance persister and its repository connection
	 * 
	 * @param connection
	 *            the semantic repository connection
	 */
	public SemanticInstancePersister(RepositoryConnection connection) {
		this.connection = connection;
	}

	@Override
	public void persist(Instance toPersist) {
		QueryBuilder insertBuilder = new SemanticQueryBuilder(SemanticQueryType.INSERT_DATA);
		buildAndEvaluateDataQuery(toPersist, insertBuilder);
	}

	@Override
	public void remove(Instance toRemove) {
		SemanticUpdateQuery removeQuery = new SemanticDataQuery(buildSemanticDeleteQuery());
		removeQuery.bindInstance(VALUE, toRemove);
		removeQuery.evaluate(connection);
	}

	@Override
	public void update(Instance toUpdate) {
		// Remove all triplets connected to this instance
		remove(toUpdate);
		// Add the instance to the semantic store
		persist(toUpdate);
	}

	@Override
	public void update(List<Instance> toUpdate) {
		// Remove all triplets connected to all instances in the list
		remove(toUpdate);
		// Add the list of instances to the semantic store
		persist(toUpdate);
	}

	@Override
	public void persist(List<Instance> toPersist) {
		// Individually persist all instances inside the list
		for (Instance instance : toPersist) {
			persist(instance);
		}
	}

	@Override
	public void remove(List<Instance> toRemove) {
		// Individually remove all instances inside the list
		for (Instance instance : toRemove) {
			remove(instance);
		}
	}

	/**
	 * Builds and evaluates a simple data query based on a given query builder and an instance. If the instance has no
	 * properties and values the evaluation will not be successful
	 * 
	 * @param source
	 *            the source instance to be evaluated inside the query
	 * @param builder
	 *            the query builder
	 */
	private void buildAndEvaluateDataQuery(Instance source, QueryBuilder builder) {
		QueryStatementBuilder stmBuilder = null;

		URI instanceUri = source.getInstanceValue();
		for (Map.Entry<Instance, ArrayList<Instance>> pair : source.getPropertyMap().entrySet()) {
			URI property = pair.getKey().getInstanceValue();
			ArrayList<Instance> values = pair.getValue();
			for (Instance value : values) {
				stmBuilder = builder.appendStatement(instanceUri.toString(), property.toString(),
						value.getInstanceValue().toString());
			}
		}

		if (stmBuilder != null) {
			SemanticUpdateQuery query = new SemanticDataQuery(stmBuilder.compile());
			query.evaluate(connection);
		}
	}

}
