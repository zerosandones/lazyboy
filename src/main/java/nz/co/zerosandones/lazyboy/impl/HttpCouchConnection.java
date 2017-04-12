package nz.co.zerosandones.lazyboy.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nz.co.zerosandones.lazyboy.CouchConnection;
import nz.co.zerosandones.lazyboy.ResponseException;
import nz.co.zerosandones.lazyboy.ServerConnectionException;


/** 
 * Default implementation of CouchConnection interface that uses HttpURLConnections to 
 * communicate with the couchDB server.
 * 
 * @author Dave Glendenning(zerosandoneschch@gmail.com)
 *
 */
public class HttpCouchConnection implements CouchConnection {
	
	private URL serverAddress;
	private HttpConnectionFactory connectionFactory;
	
	
	private Logger logger = LogManager.getLogger(HttpCouchConnection.class);
	
	protected HttpCouchConnection(URL serverAddress, HttpConnectionFactory connectionFactory) throws ResponseException, ServerConnectionException, IOException{
		this.serverAddress = serverAddress;
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

}
