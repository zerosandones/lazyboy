package nz.co.zerosandones.lazyboy;

/**
 * Exception to be throw if there is an error with the connection to the CouchDB
 * server.
 * 
 * @author Dave Glendenning(zerosandoneschch@gmail.com)
 *
 */
public class ServerConnectionException extends Exception {
	
	public ServerConnectionException(String message){
		super(message);
	}
	
	public ServerConnectionException(String message, Throwable cause){
		super(message, cause);
	}

}
