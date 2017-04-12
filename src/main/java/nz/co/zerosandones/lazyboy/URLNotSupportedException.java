package nz.co.zerosandones.lazyboy;

import java.net.URL;

/**
 * Exception that is thrown if a HttpConnectionFactory can not make
 * a HttpURLConnection from a given URL.
 * 
 * @author Dave Glendenning (zerosandoneschch@gmail.com)
 *
 */
public class URLNotSupportedException extends RuntimeException {
	
	private URL urlAddress;
	
	public URLNotSupportedException(URL urlAddress){
		this.urlAddress = urlAddress;
	}
	
	public URL getUrlAddress(){
		return urlAddress;
	}

}
