package com.ontotext.semantic.core.main;

import static com.ontotext.semantic.core.common.SemanticNamespaceUtil.buildInstanceLongUri;
import static com.ontotext.semantic.core.common.SemanticNamespaceUtil.buildLiteralLongUri;
import static com.ontotext.semantic.core.common.SemanticNamespaceUtil.parseToRawNamespace;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.OBJECT;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.PREDICATE;
import static com.ontotext.semantic.core.common.SemanticSearchUtil.SUBJECT;

import java.io.IOException;
import java.util.List;

import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

import com.ontotext.semantic.api.enumeration.ArithmeticOperators;
import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.query.SemanticTupleQuery;
import com.ontotext.semantic.api.query.SemanticUpdateQuery;
import com.ontotext.semantic.api.query.builders.QueryCompilator;
import com.ontotext.semantic.core.common.SemanticNamespaceUtil;
import com.ontotext.semantic.core.repository.EmbededSemantics;
import com.ontotext.semantic.impl.query.SemanticDataQuery;
import com.ontotext.semantic.impl.query.SemanticModifyQuery;
import com.ontotext.semantic.impl.query.SemanticSelectQuery;
import com.ontotext.semantic.impl.query.SemanticTupleQueryParser;
import com.ontotext.semantic.impl.query.builders.SemanticQueryBuilder;
import com.ontotext.semantic.impl.structures.SemanticTriplet;

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

	public static QueryCompilator buildSemanticSelectQuery() {
		return new SemanticQueryBuilder(SemanticQueryType.SELECT)
				.appendStatement(new SemanticTriplet(SUBJECT, PREDICATE, OBJECT))
				.appendCondition(new SemanticTriplet(SUBJECT, "rdf:type", "ontoperson:Person"))
				.appendFilter(new SemanticTriplet(SUBJECT, ArithmeticOperators.EQUALS, "?value"))
				.getQueryCompilator();
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

		SemanticTupleQuery query = new SemanticSelectQuery(buildSemanticSelectQuery().getLongFormatQuery());
		query.bind("value", buildInstanceLongUri("dataperson:John"));

		// Construct modification query - delete
		String delQuery = SemanticNamespaceUtil
				.parseToRawNamespace(
						"DELETE { ?s ?p ?o. } WHERE { ?s ?p ?o. ?s rdf:type ontoperson:Person. FILTER(?s = ?k || ?o = ?k). } ");
		SemanticUpdateQuery deletePerson = new SemanticModifyQuery(delQuery);
		deletePerson.bind("k", buildInstanceLongUri("dataperson:" + person));

		// Construct data query - delete
		String deQuery = parseToRawNamespace("DELETE DATA { ?p ?f ?v. }");
		SemanticUpdateQuery delete = new SemanticDataQuery(deQuery);
		delete.bind("p", buildInstanceLongUri("dataperson:John"));
		delete.bind("f", buildInstanceLongUri("ontoperson:hasValue"));
		delete.bind("v", buildLiteralLongUri("42", XMLSchema.INTEGER));

		// Construct data query - insert
		String upQuery = parseToRawNamespace("INSERT DATA { ?p ?f ?v. }");
		SemanticUpdateQuery update = new SemanticDataQuery(upQuery);
		update.bind("p", buildInstanceLongUri("dataperson:John"));
		update.bind("f", buildInstanceLongUri("ontoperson:hasValue"));
		update.bind("v", buildLiteralLongUri("23", XMLSchema.INTEGER));

		// Evaluate data query
		delete.evaluate(connection);
		// Evaluate data query
		update.evaluate(connection);
		// Evaluate modification query
		// deletePerson.evaluate(connection);
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
