package nz.co.zerosandones.lazyboy;

import java.io.IOException;

/**
 * A CouchConnection provides the interface for connections to a CouchDB
 * server and allows the running of commands against the sever.
 * 
 * @author Dave Glendenning(zerosandoneschch@gmail.com)
 *
 */
public interface CouchConnection {
	
	/**
	 * Creates a database on the couchDB server with the given name
	 * 
	 * @param databaseName the name of the database to be made
	 * @throws IOException if there was an error with the connection to the CouchDB server
	 * @throws DatabaseExistsException if a database with the given name already exists
	 * @throws DatabaseNameException if the name of the database is invalid
	 * @throws ResponseException if the response from the server is unexpected
	 */
	public void createDatabase(String databaseName) throws IOException, DatabaseExistsException, DatabaseNameException, ResponseException;
	
	/**
	 * Deletes the database on the CouchDB server with the given name
	 * 
	 * @param databaseName the name of the database to delete
	 * @throws IOException if there was an error with the connection to the CouchDB server
	 * @throws DatabaseNotFoundException if the database does not exist
	 * @throws DatabaseNameException if the name of the database is invalid
	 * @throws ResponseException if the response from the server is unexpected
	 */
	public void deleteDatabase(String databaseName) throws IOException, DatabaseNotFoundException, DatabaseNameException, ResponseException;
}
