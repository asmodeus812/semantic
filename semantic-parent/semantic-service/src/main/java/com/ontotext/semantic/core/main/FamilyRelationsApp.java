package com.ontotext.semantic.core.main;

import static com.ontotext.semantic.core.common.SemanticNamespaceUtil.buildInstanceLongUri;
import static com.ontotext.semantic.core.common.SemanticNamespaceUtil.buildLiteralLongUri;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.OBJECT;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.PREDICATE;
import static com.ontotext.semantic.core.common.SemanticSparqlUtil.SUBJECT;

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
import com.ontotext.semantic.api.enumeration.LogicalOperators;
import com.ontotext.semantic.api.enumeration.SemanticQueryType;
import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.query.SemanticTupleQuery;
import com.ontotext.semantic.api.query.SemanticUpdateQuery;
import com.ontotext.semantic.api.query.compiler.QueryCompiler;
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

	public static QueryCompiler buildSemanticSelectQuery() {
		return new SemanticQueryBuilder(SemanticQueryType.SELECT)
				.appendStatement(new SemanticTriplet(SUBJECT, PREDICATE, OBJECT))
				.appendCondition(new SemanticTriplet(SUBJECT, "rdf:type", "ontoperson:Person"))
				.appendFilter(new SemanticTriplet(SUBJECT, ArithmeticOperators.EQUALS, "?value"))
				.compile();
	}

	public static QueryCompiler buildSemanticDeleteQuery() {
		return new SemanticQueryBuilder(SemanticQueryType.DELETE)
				.appendStatement(new SemanticTriplet(SUBJECT, PREDICATE, OBJECT))
				.appendCondition(new SemanticTriplet(SUBJECT, "rdf:type", "ontoperson:Person"))
				.appendFilter(new SemanticTriplet(SUBJECT, ArithmeticOperators.EQUALS, "?value"))
				.appendLogicalOperator(LogicalOperators.OR)
				.appendFilter(new SemanticTriplet(OBJECT, ArithmeticOperators.EQUALS, "?value"))
				.compile();
	}

	public static QueryCompiler buildSemanticDataQuery(SemanticQueryType type) {
		return new SemanticQueryBuilder(type)
				.appendStatement(new SemanticTriplet(SUBJECT, PREDICATE, OBJECT))
				.compile();
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

		SemanticTupleQuery query = new SemanticSelectQuery(buildSemanticSelectQuery().compileLongFormatQuery());
		query.bind("value", buildInstanceLongUri("dataperson:John"));

		// Construct modification query - delete
		SemanticUpdateQuery deletePerson = new SemanticModifyQuery(buildSemanticDeleteQuery().compileLongFormatQuery());
		deletePerson.bind("value", buildInstanceLongUri("dataperson:Mary"));

		// Construct data query - delete
		SemanticUpdateQuery delete = new SemanticDataQuery(
				buildSemanticDataQuery(SemanticQueryType.DELETE_DATA).compileLongFormatQuery());
		delete.bind(SUBJECT, buildInstanceLongUri("dataperson:John"));
		delete.bind(PREDICATE, buildInstanceLongUri("ontoperson:hasValue"));
		delete.bind(OBJECT, buildLiteralLongUri("42", XMLSchema.INTEGER));

		// Construct data query - insert
		SemanticUpdateQuery update = new SemanticDataQuery(
				buildSemanticDataQuery(SemanticQueryType.INSERT_DATA).compileLongFormatQuery());
		update.bind(SUBJECT, buildInstanceLongUri("dataperson:John"));
		update.bind(PREDICATE, buildInstanceLongUri("ontoperson:hasValue"));
		update.bind(OBJECT, buildLiteralLongUri("12", XMLSchema.INTEGER));

		// Evaluate data query
		delete.evaluate(connection);
		// Evaluate data query
		update.evaluate(connection);
		// Evaluate modification query
		deletePerson.evaluate(connection);
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
