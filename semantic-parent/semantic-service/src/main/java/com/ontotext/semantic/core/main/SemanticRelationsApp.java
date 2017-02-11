package com.ontotext.semantic.core.main;

import static com.ontotext.semantic.core.common.SemanticNamespaceUtil.buildInstanceLongUri;
import static com.ontotext.semantic.impl.common.SemanticPrebuiltQuery.buildSemanticSelectQuery;

import java.io.IOException;
import java.util.List;

import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.instance.InstanceChain;
import com.ontotext.semantic.api.instance.InstanceParser;
import com.ontotext.semantic.api.persist.SemanticPersister;
import com.ontotext.semantic.api.query.SemanticTupleQuery;
import com.ontotext.semantic.core.repository.EmbededSemantics;
import com.ontotext.semantic.impl.instance.SemanticInstanceChain;
import com.ontotext.semantic.impl.instance.SemanticInstanceParser;
import com.ontotext.semantic.impl.persist.SemanticInstancePersister;
import com.ontotext.semantic.impl.query.SemanticSelectQuery;
import com.ontotext.semantic.impl.query.parser.SemanticTupleQueryParser;

/**
 * An example that illustrates loading of vocabulary, data, querying and modifying data.
 * 
 * @author Ontotext
 */
public class SemanticRelationsApp {
	private RepositoryConnection connection;

	/**
	 * @param connection
	 */
	public SemanticRelationsApp(RepositoryConnection connection) {
		this.connection = connection;
	}

	/**
	 * Loads the ontology and the sample data into the repository.
	 *
	 * @throws RepositoryException
	 * @throws IOException
	 * @throws RDFParseException
	 */
	public void loadData() throws RepositoryException, IOException, RDFParseException {
		System.out.println("# Loading ontology and data");
		connection.begin();
		connection.add(SemanticRelationsApp.class.getResourceAsStream("/ontology.ttl"), "urn:base",
				RDFFormat.TURTLE);
		connection.add(SemanticRelationsApp.class.getResourceAsStream("/datastore.ttl"), "urn:base",
				RDFFormat.TURTLE);
		connection.commit();
	}

	/**
	 * Executes a sample query listing and unwrapping all instances inside of it
	 * 
	 */
	public void executeSampleQueries() {
		System.out.println("# Listing all instances");

		// Construct tuple query - select
		SemanticTupleQuery selectDrivers = new SemanticSelectQuery(buildSemanticSelectQuery());
		selectDrivers.bind("type", buildInstanceLongUri("class:driver"));

		SemanticTupleQuery selectAutomobiles = new SemanticSelectQuery(buildSemanticSelectQuery());
		selectAutomobiles.bind("type", buildInstanceLongUri("class:automobile"));

		// Evaluate all automobiles
		List<Instance> automobiles = new SemanticTupleQueryParser().parseQuery(connection, selectAutomobiles);

		// Remove all automobiles from the semantic store
		SemanticPersister<Instance> persister = new SemanticInstancePersister(connection);
		persister.remove(automobiles);

		// Evaluate all drivers data from the semantic store
		List<Instance> drivers = new SemanticTupleQueryParser().parseQuery(connection, selectDrivers);

		// Unwrap all instance in the result set
		InstanceChain chain = new SemanticInstanceChain(connection);
		chain.unwrap(drivers);

		// Parse & Log the result out to the console
		InstanceParser parser = new SemanticInstanceParser();
		System.out.println(parser.toString(drivers));
	}

	public static void main(String[] args) throws Exception {

		// RemoteSemantics remote = new RemoteSemantics(new URL("http://localhost:7200"));
		// Repository repo = remote.createRepository("family");
		// RepositoryConnection connection = remote.createConnection(repo);

		RepositoryConnection connection = EmbededSemantics.openConnectionToTemporaryRepository("owl2-rl-optimized");
		connection.clear();

		SemanticRelationsApp familyRelations = new SemanticRelationsApp(connection);
		try {
			familyRelations.loadData();
			familyRelations.executeSampleQueries();
		} finally {
			connection.close();
			// remote.close();
		}
	}
}
