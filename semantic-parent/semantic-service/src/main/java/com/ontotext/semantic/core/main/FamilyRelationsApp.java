package com.ontotext.semantic.core.main;

import java.io.IOException;
import java.util.List;

import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.query.SemanticTupleQuery;
import com.ontotext.semantic.api.query.SemanticUpdateQuery;
import com.ontotext.semantic.core.common.SemanticNamespaceUtil;
import com.ontotext.semantic.core.repository.EmbededSemantics;
import com.ontotext.semantic.impl.query.SemanticModifyQuery;
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

		connection.add(FamilyRelationsApp.class.getResourceAsStream("/family-ontology.ttl"), "urn:base",
				RDFFormat.TURTLE);

		connection.add(FamilyRelationsApp.class.getResourceAsStream("/family-data.ttl"), "urn:base", RDFFormat.TURTLE);

		connection.commit();
	}

	/**
	 * Lists family relations for a given person. The output will be printed to standard out.
	 *
	 * @param person
	 *            a person (the local part of a URI)
	 * @throws RepositoryException
	 * @throws MalformedQueryException
	 * @throws QueryEvaluationException
	 */
	public void listRelationsForPerson(String person)
			throws RepositoryException, MalformedQueryException, QueryEvaluationException {
		System.out.println("# Listing all properties for " + person);

		// Construct tuple query - select
		String rawQuery = SemanticNamespaceUtil.parseToRawNamespace(
				"SELECT ?s ?p ?o WHERE { ?s ?p ?o. ?s rdf:type ontoperson:Person. FILTER(?o > ?k && ?o < ?m) }");
		SemanticTupleQuery query = new SemanticSelectQuery(rawQuery);
		query.bind("s", SemanticNamespaceUtil.buildInstanceLongUri("dataperson:" + person));
		query.bind("m", SemanticNamespaceUtil.buildLiteralLongUri("50", XMLSchema.INTEGER));
		query.bind("k", SemanticNamespaceUtil.buildLiteralLongUri("10", XMLSchema.INTEGER));

		// Construct modification query - delete
		String deQuery = SemanticNamespaceUtil.parseToRawNamespace("DELETE DATA { ?p ?f ?v. }");
		SemanticUpdateQuery delete = new SemanticModifyQuery(deQuery);
		delete.bind("p", SemanticNamespaceUtil.buildInstanceLongUri("dataperson:John"));
		delete.bind("f", SemanticNamespaceUtil.buildInstanceLongUri("ontoperson:hasValue"));
		delete.bind("v", SemanticNamespaceUtil.buildLiteralLongUri("42", XMLSchema.INTEGER));

		// Construct modification query - insert
		String upQuery = SemanticNamespaceUtil.parseToRawNamespace("INSERT DATA { ?p ?f ?v. }");
		SemanticUpdateQuery update = new SemanticModifyQuery(upQuery);
		update.bind("p", SemanticNamespaceUtil.buildInstanceLongUri("dataperson:John"));
		update.bind("f", SemanticNamespaceUtil.buildInstanceLongUri("ontoperson:hasValue"));
		update.bind("v", SemanticNamespaceUtil.buildLiteralLongUri("23", XMLSchema.INTEGER));

		// Evaluate modification query
		delete.evaluate(connection);
		// Evaluate modification query
		update.evaluate(connection);
		// Evaluate tuple query results
		List<Instance> instances = new SemanticTupleQueryParser().parseQuery(connection, query);

		// Log the result out on the console
		System.out.println(instances);
	}

	public static void main(String[] args) throws Exception {

		// RemoteSemantics remote = new RemoteSemantics(new URL("http://localhost:7200"));
		// Repository repo = remote.createRepository("family");
		// RepositoryConnection connection = remote.createConnection(repo);

		// Manual embeded repository creation for testing
		RepositoryConnection connection = EmbededSemantics.openConnectionToTemporaryRepository("owl2-rl-optimized");

		connection.clear();

		FamilyRelationsApp familyRelations = new FamilyRelationsApp(connection);

		try {
			familyRelations.loadData();

			familyRelations.listRelationsForPerson("John");

		} finally {
			connection.close();
			// remote.close();
		}
	}
}
