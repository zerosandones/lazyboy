package nz.co.zerosandones.lazyboy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import nz.co.zerosandones.lazyboy.ServerConnectionException;
import nz.co.zerosandones.lazyboy.URLNotSupportedException;

@RunWith(MockitoJUnitRunner.class)
public class HttPCouchConnectionTest {
	
	@Mock
	private HttpConnectionFactory cf;
	@Mock
	private HttpURLConnection initialConnection;
	@Mock
	private HttpURLConnection requestConnection;
	private URL serverAddress;
	
	private HttpCouchConnection couchConnection;

	@Before
	public void setUp() throws Exception {
		serverAddress = new URL("http://localhost");
		
		when(cf.createConnection()).thenReturn(initialConnection);
		
		when(initialConnection.getResponseCode()).thenReturn(200);
		when(initialConnection.getHeaderField("Server")).thenReturn("CouchDB (Erlang/OTP)");
	}
	
	@Test
	public void createCouchConnectionTest() throws URLNotSupportedException, IOException, ServerConnectionException{
		couchConnection = new HttpCouchConnection(serverAddress, cf);
		
		verify(cf).createConnection();
		verify(initialConnection).getResponseCode();
		verify(initialConnection).getHeaderField("Server");
	}
	
	@Test(expected=ServerConnectionException.class)
	public void createCouchConnetionToNotCouchDBServerTest() throws URLNotSupportedException, IOException, ServerConnectionException{
		HttpURLConnection initialConnectionToIncorrectServer = mock(HttpURLConnection.class);
		
		when(cf.createConnection()).thenReturn(initialConnectionToIncorrectServer);
		
		when(initialConnectionToIncorrectServer.getResponseCode()).thenReturn(200);
		when(initialConnectionToIncorrectServer.getHeaderField("Server")).thenReturn("not couchdb");
		
		couchConnection = new HttpCouchConnection(serverAddress, cf);
	}

}
