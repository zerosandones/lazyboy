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
	 * Creates a database on the couchDB server
	 * 
	 * @param databaseName the name of the database to be made
	 */
	public void createDatabase(String databaseName) throws IOException, DatabaseExistsException, DatabaseNameException;
	

}
