package com.ontotext.graphdb.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.manager.RemoteRepositoryManager;
import org.openrdf.repository.manager.RepositoryManager;

import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.query.SemanticTupleQuery;
import com.ontotext.semantic.api.query.SemanticUpdateQuery;
import com.ontotext.semantic.core.common.SemanticNamespaceUtil;
import com.ontotext.semantic.impl.query.SemanticModifyQuery;
import com.ontotext.semantic.impl.query.SemanticSelectQuery;
import com.ontotext.semantic.impl.query.SemanticTupleQueryParser;

@Stateless
@Path("/semantic")
public class SemanticService {

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Instance> getString() throws Exception {

		RepositoryManager repositoryManager = new RemoteRepositoryManager("http://localhost:7200");
		repositoryManager.initialize();

		Repository repository = repositoryManager.getRepository("repository");
		RepositoryConnection connection = repository.getConnection();

		String person = "John";

		System.out.println("# Listing all properties for " + person);

		// Construct tuple query - select
		String rawQuery = SemanticNamespaceUtil.parseToRawNamespace(
				"SELECT ?s ?p ?o WHERE { ?s ?p ?o." + " ?s rdf:type ontoperson:Person. FILTER(?o > ?k && ?o < ?m) }");
		SemanticTupleQuery query = new SemanticSelectQuery(rawQuery);
		query.bind("s", SemanticNamespaceUtil.buildInstanceLongUri("dataperson:" + person));
		query.bind("m", SemanticNamespaceUtil.buildLiteralLongUri("50", XMLSchema.INTEGER));
		query.bind("k", SemanticNamespaceUtil.buildLiteralLongUri("10", XMLSchema.INTEGER));

		// Construct modification query - insert
		String upQuery = SemanticNamespaceUtil.parseToRawNamespace("INSERT DATA { ?p ?f ?v. }");
		SemanticUpdateQuery update = new SemanticModifyQuery(upQuery);
		update.bind("p", SemanticNamespaceUtil.buildInstanceLongUri("dataperson:John"));
		update.bind("f", SemanticNamespaceUtil.buildInstanceLongUri("ontoperson:hasValue"));
		update.bind("v", SemanticNamespaceUtil.buildLiteralLongUri("23", XMLSchema.INTEGER));

		// Construct modification query - delete
		String deQuery = SemanticNamespaceUtil.parseToRawNamespace("DELETE DATA { ?p ?f ?v. }");
		SemanticUpdateQuery delete = new SemanticModifyQuery(deQuery);
		delete.bind("p", SemanticNamespaceUtil.buildInstanceLongUri("dataperson:John"));
		delete.bind("f", SemanticNamespaceUtil.buildInstanceLongUri("ontoperson:hasValue"));
		delete.bind("v", SemanticNamespaceUtil.buildLiteralLongUri("42", XMLSchema.INTEGER));

		// Evaluate modification query
		update.evaluate(connection);
		// Evaluate modification query
		delete.evaluate(connection);
		// Evaluate tuple query results
		List<Instance> instances = new SemanticTupleQueryParser().parseQuery(connection, query);

		// Log the result out on the console
		System.out.println(instances);

		return instances;
	}
}
