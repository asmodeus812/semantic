package com.ontotext.graphdb.rest;

import static com.ontotext.semantic.impl.common.SemanticPrebuiltQuery.buildSemanticSelectQuery;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.core.api.annotation.Inject;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.api.instance.InstanceChain;
import com.ontotext.semantic.api.persist.SemanticPersister;
import com.ontotext.semantic.api.query.SemanticTupleQuery;
import com.ontotext.semantic.core.repository.RemoteSemantics;
import com.ontotext.semantic.impl.instance.SemanticInstance;
import com.ontotext.semantic.impl.parser.SemanticTupleQueryParser;
import com.ontotext.semantic.impl.query.SemanticSelectQuery;

@Stateless
@Path("/semantic")
public class SemanticService {

	@Inject
	private InstanceChain chain;

	@Inject
	private SemanticPersister<Instance> persister;

	private RemoteSemantics remote;

	private Repository repo;

	private RepositoryConnection connection;

	@PostConstruct
	public void constructConnection() throws MalformedURLException {
		remote = new RemoteSemantics(new URL("http://localhost:7200"));
		repo = remote.createRepository("framework");
		connection = remote.createConnection(repo);
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Instance> getString(String json) throws Exception {
		SemanticTupleQuery selectDrivers = new SemanticSelectQuery(buildSemanticSelectQuery());
		selectDrivers.bind("type", new SemanticInstance("class:driver"));
		List<Instance> drivers = new SemanticTupleQueryParser().parseQuery(connection, selectDrivers);

		return drivers;
	}

	@PreDestroy
	public void destroyConnection() throws RepositoryException, IOException {
		connection.close();
		remote.close();
	}
}
