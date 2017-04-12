package nz.co.zerosandones.lazyboy.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nz.co.zerosandones.lazyboy.URLNotSupportedException;

/**
 * Factory for creating HttpURLConnection objects from a given base URL.
 * 
 * One of the reasons for this class is for unit testing. Since URL objects are final they
 * can not be mocked, this class can be mocked which can then create mocked HttpURLConnection
 * objects. 
 * 
 * @author Dave Glendenning(zerosandoneschch@gmail.com)
 *
 */
class HttpConnectionFactory {
	
	
	private URL baseURL;
	
	private Logger logger = LogManager.getLogger(HttpConnectionFactory.class);
	
	/**
	 * Constructor for creating a new HttpConnectionFactory
	 * 
	 * @param baseURL the base URL which this factory will use to make HttpURLConnection objects
	 * @throws URLNotSupportedException if the URL can not be used to create a HttpURLConnection
	 * @throws IllegalArgumentException if the URL is null
	 */
	public HttpConnectionFactory(URL baseURL) throws URLNotSupportedException, IllegalArgumentException{
		if(baseURL == null){
			logger.error("baseURL is null");
			throw new IllegalArgumentException("baseURL is null");
		}
		if("http".equals(baseURL.getProtocol().toLowerCase()) || "https".equals(baseURL.getProtocol().toLowerCase())){
			logger.debug("url protocol is supported");
			this.baseURL = baseURL;
		}else{
			logger.error("URL protocol {} is not supported", baseURL.getProtocol());
			throw new URLNotSupportedException(baseURL);
		}
		
	}
	
	/**
	 * Creates a HttpConnection to the base URL
	 * 
	 * @return a HttpConnection to the URL
	 * @throws IOException if there is a problem while creating the URLConnection
	 */
	public HttpURLConnection createConnection() throws IOException{
		logger.debug("creating new HttpURLConnection");
		return (HttpURLConnection)this.baseURL.openConnection();
		
	}

}
