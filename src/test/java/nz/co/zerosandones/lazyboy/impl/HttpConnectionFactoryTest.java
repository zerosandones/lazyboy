package nz.co.zerosandones.lazyboy.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import nz.co.zerosandones.lazyboy.URLNotSupportedException;

public class HttpConnectionFactoryTest {
	
	private HttpConnectionFactory cf;

	@Test
	public void createHttpURLConnectionFromBaseURL() throws URLNotSupportedException, IOException {
		URL address = new URL("http://localhost");
		cf = new HttpConnectionFactory(address);
		HttpURLConnection connection = cf.createConnection();
		assertNotNull(connection);
		assertEquals("http://localhost", connection.getURL().toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createHttpURLConnectionWithNullURL(){
		URL address = null;
		cf = new HttpConnectionFactory(address);
		
	}
	
	@Test(expected=URLNotSupportedException.class)
	public void createHttpConnectionFactoryWithUnsupportProtocol() throws URLNotSupportedException, IOException{
		URL address = new URL("ftp://localhost");
		cf = new HttpConnectionFactory(address);
	}

}
