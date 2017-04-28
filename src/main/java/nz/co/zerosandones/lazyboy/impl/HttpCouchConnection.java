package nz.co.zerosandones.lazyboy.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nz.co.zerosandones.lazyboy.CouchConnection;
import nz.co.zerosandones.lazyboy.DatabaseExistsException;
import nz.co.zerosandones.lazyboy.DatabaseNameException;
import nz.co.zerosandones.lazyboy.ResponseException;
import nz.co.zerosandones.lazyboy.ServerConnectionException;


/** 
 * Default implementation of CouchConnection interface that uses HttpURLConnections to 
 * communicate with the couchDB server.
 *
 */
public class HttpCouchConnection implements CouchConnection {
	
	private HttpConnectionFactory connectionFactory;
	
	private Logger logger = LogManager.getLogger(HttpCouchConnection.class);
	
	protected HttpCouchConnection(HttpConnectionFactory connectionFactory) throws ResponseException, ServerConnectionException, IOException{
		this.connectionFactory = connectionFactory;
		
		HttpURLConnection couchDBConnection = this.connectionFactory.createConnection();
		int responseCode = couchDBConnection.getResponseCode();
		if(responseCode == 200){
			String serverHeader = couchDBConnection.getHeaderField("Server");
			if(serverHeader == null || !serverHeader.trim().startsWith("CouchDB")){
				logger.error("'Server' header has incorrect value expecting 'CouchDB ...', received {}", serverHeader);
				throw new ServerConnectionException("Server was not a CouchDB server instance");
			}
			logger.debug("connection accepted");
		}else{
			String message = "Server responded with code " + responseCode;
			logger.error(message);
			throw new ResponseException("Response from server not expected value", responseCode);
		}
		
	}

	@Override
	public void createDatabase(String databaseName) throws IOException, DatabaseNameException, DatabaseExistsException {
		logger.trace("createDatabase(String {})", databaseName);
		HttpURLConnection couchDBConnection = this.connectionFactory.createConnection(databaseName);
		couchDBConnection.setRequestMethod("PUT");
		int responseCode = couchDBConnection.getResponseCode();
		if(responseCode == 200){
			logger.debug("database {} made", databaseName);
		}else if(responseCode == 400){
			logger.error("database name {} is invalid", databaseName);
			throw new DatabaseNameException();
		}else if(responseCode == 412){
			logger.error("database name {} already exists", databaseName);
			throw new DatabaseExistsException();
		}
		
	}

}
