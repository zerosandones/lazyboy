package nz.co.zerosandones.lazyboy;

/**
 * Exception thrown when a request to the CouchDB comes back with a response
 * code or content that is not as expected
 * 
 * @author David Glendenning (zerosandoneschch@gmail.com)
 *
 */
public class ResponseException extends RuntimeException {
	
	private int responseCode;
	
	/**
	 * Constructor to create a ResponseException with the given message and responseCode
	 * 
	 * @param message the reason the exception was thrown
	 * @param responseCode the http response code the server responded with
	 */
	public ResponseException(String message, int responseCode){
		super(message);
		this.responseCode = responseCode;
	}
	
	/**
	 * Get the response code the server responded with
	 * 
	 * @return the http response code the server responded with
	 */
	public int getResponseCode(){
		return this.responseCode;
	}
	

}
