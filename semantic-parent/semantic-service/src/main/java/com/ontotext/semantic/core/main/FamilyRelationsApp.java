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
import com.ontotext.semantic.api.query.SemanticTupleQuery;
import com.ontotext.semantic.core.repository.EmbededSemantics;
import com.ontotext.semantic.impl.instance.SemanticInstanceChain;
import com.ontotext.semantic.impl.instance.SemanticInstanceParser;
import com.ontotext.semantic.impl.query.SemanticSelectQuery;
import com.ontotext.semantic.impl.query.SemanticTupleQueryParser;

/**
 * An example that illustrates loading of vocabulary, data, querying and modifying data.
 * 
 * @author Ontotext
 */
public class FamilyRelationsApp {
	private RepositoryConnection connection;

	/**
	 * @param connection
	 */
	public FamilyRelationsApp(RepositoryConnection connection) {
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
		connection.add(FamilyRelationsApp.class.getResourceAsStream("/ontology.ttl"), "urn:base",
				RDFFormat.TURTLE);
		connection.add(FamilyRelationsApp.class.getResourceAsStream("/datastore.ttl"), "urn:base",
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
		SemanticTupleQuery query = new SemanticSelectQuery(buildSemanticSelectQuery().longFormatQuery());
		query.bind("type", buildInstanceLongUri("class:person"));

		// Evaluate tuple query results
		List<Instance> instances = new SemanticTupleQueryParser().parseQuery(connection, query);

		// Unwrap all instance in the result set
		InstanceChain chain = new SemanticInstanceChain(connection);
		chain.unwrap(instances);

		// Parse & Log the result out to the console
		InstanceParser parser = new SemanticInstanceParser();
		System.out.println(parser.toString(instances));
	}

	public static void main(String[] args) throws Exception {

		// RemoteSemantics remote = new RemoteSemantics(new URL("http://localhost:7200"));
		// Repository repo = remote.createRepository("family");
		// RepositoryConnection connection = remote.createConnection(repo);

		RepositoryConnection connection = EmbededSemantics.openConnectionToTemporaryRepository("owl2-rl-optimized");
		connection.clear();

		FamilyRelationsApp familyRelations = new FamilyRelationsApp(connection);
		try {
			familyRelations.loadData();
			familyRelations.executeSampleQueries();
		} finally {
			connection.close();
			// remote.close();
		}
	}
}
