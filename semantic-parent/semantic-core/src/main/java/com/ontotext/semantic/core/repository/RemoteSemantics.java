package com.ontotext.semantic.core.repository;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.manager.RemoteRepositoryManager;
import org.openrdf.repository.manager.RepositoryManager;

import com.ontotext.semantic.api.exception.SemanticConnectionException;
import com.ontotext.semantic.api.exception.SemanticRepositoryException;

/**
 * Represents a remote semantic connector. This class is able to create multiple connections to different semantic
 * repositories located on the same connection point. Usually the connection point is a remote workbench
 * 
 * @author Svetlozar
 */
public class RemoteSemantics implements Closeable {

	private URL connectionPoint;
	private RepositoryManager repositoryManager;

	/**
	 * Initializes the Remote semantics and connects to a given URL
	 * 
	 * @param connectionPoint
	 *            the connection point for the remote semantics
	 */
	public RemoteSemantics(URL connectionPoint) {
		this.connectionPoint = connectionPoint;
		initializeRemoteConnection();
	}

	/**
	 * Creates a Repository object based on an existing connection to a data base
	 * 
	 * @param repository
	 *            the repository name
	 * @return the created Repository object
	 */
	public Repository createRepository(String repository) {
		try {
			return repositoryManager.getRepository(repository);
		} catch (RepositoryConfigException | RepositoryException e) {
			throw new SemanticRepositoryException("Invalid repository specified " + e.getMessage());
		}
	}

	/**
	 * Creates a RepositoryConnection for a given Repository
	 * 
	 * @param repository
	 *            the Repository to which a connection should be established
	 * @return the Repository connection
	 */
	public RepositoryConnection createConnection(Repository repository) {
		try {
			return repository.getConnection();
		} catch (RepositoryException e) {
			throw new SemanticRepositoryException("Unable to connect to specified repository " + e.getMessage());
		}
	}

	/**
	 * Returns the underlying Repository manager
	 * 
	 * @return the underlying Repository manager
	 */
	public RepositoryManager getRepositoryManager() {
		return repositoryManager;
	}

	/**
	 * Returns the connection point or path
	 * 
	 * @return the connection point
	 */
	public URL getConnectionPoint() {
		return connectionPoint;
	}

	@Override
	public void close() throws IOException {
		repositoryManager.shutDown();
	}

	private void initializeRemoteConnection() {
		this.repositoryManager = new RemoteRepositoryManager(this.connectionPoint.toString());
		try {
			repositoryManager.initialize();
		} catch (RepositoryException e) {
			throw new SemanticConnectionException("Invalid remote connection point specified " + e.getMessage());
		}
	}
}
