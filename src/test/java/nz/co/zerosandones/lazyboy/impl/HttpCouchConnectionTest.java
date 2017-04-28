package nz.co.zerosandones.lazyboy.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import nz.co.zerosandones.lazyboy.DatabaseExistsException;
import nz.co.zerosandones.lazyboy.DatabaseNameException;
import nz.co.zerosandones.lazyboy.DatabaseNotFoundException;
import nz.co.zerosandones.lazyboy.ResponseException;
import nz.co.zerosandones.lazyboy.ServerConnectionException;

@RunWith(MockitoJUnitRunner.class)
public class HttpCouchConnectionTest {
	
	@Mock
	private HttpConnectionFactory cf;
	@Mock
	private HttpURLConnection connection;

	@Before
	public void setUp() throws Exception {
		when(cf.createConnection()).thenReturn(connection);
		
		when(connection.getResponseCode()).thenReturn(200);
		when(connection.getHeaderField("Server")).thenReturn("CouchDB (Erlang/OTP)");
	}
	
	@Test
	public void createDatabaseTest() throws IOException, ResponseException, ServerConnectionException, DatabaseNameException, DatabaseExistsException{
		HttpURLConnection newDatabaseConnection = mock(HttpURLConnection.class);
		when(cf.createConnection("new_database")).thenReturn(newDatabaseConnection);
		when(newDatabaseConnection.getResponseCode()).thenReturn(201);
		
		HttpCouchConnection couchConnection = new HttpCouchConnection(cf);
		couchConnection.createDatabase("new_database");
		verify(cf).createConnection("new_database");
		verify(newDatabaseConnection).setRequestMethod("PUT");
		verify(newDatabaseConnection).getResponseCode();

	}
	
	@Test(expected=DatabaseExistsException.class)
	public void createDatabaseWithDuplicateDatabaseTest() throws IOException, ResponseException, ServerConnectionException, DatabaseNameException, DatabaseExistsException{
		HttpURLConnection newDatabaseConnection = mock(HttpURLConnection.class);
		when(cf.createConnection("new_database")).thenReturn(newDatabaseConnection);
		when(newDatabaseConnection.getResponseCode()).thenReturn(412);
		
		HttpCouchConnection couchConnection = new HttpCouchConnection(cf);
		couchConnection.createDatabase("new_database");

	}
	
	@Test(expected=DatabaseNameException.class)
	public void createDatabaseWithIncompatableNameTest() throws IOException, ResponseException, ServerConnectionException, DatabaseNameException, DatabaseExistsException{
		HttpURLConnection newDatabaseConnection = mock(HttpURLConnection.class);
		when(cf.createConnection("@new_database")).thenReturn(newDatabaseConnection);
		when(newDatabaseConnection.getResponseCode()).thenReturn(400);
		
		HttpCouchConnection couchConnection = new HttpCouchConnection(cf);
		couchConnection.createDatabase("@new_database");

	}
	
	@Test
	public void deleteDatabaseTest() throws IOException, ResponseException, ServerConnectionException, DatabaseNameException, DatabaseNotFoundException{
		HttpURLConnection newDatabaseConnection = mock(HttpURLConnection.class);
		when(cf.createConnection("new_database")).thenReturn(newDatabaseConnection);
		when(newDatabaseConnection.getResponseCode()).thenReturn(200);
		
		HttpCouchConnection couchConnection = new HttpCouchConnection(cf);
		couchConnection.deleteDatabase("new_database");
		verify(cf).createConnection("new_database");
		verify(newDatabaseConnection).setRequestMethod("DELETE");
		verify(newDatabaseConnection).getResponseCode();

	}
	
	@Test(expected=DatabaseNotFoundException.class)
	public void deleteDatabaseThatDoesNotExistTest() throws IOException, ResponseException, ServerConnectionException, DatabaseNameException, DatabaseNotFoundException{
		HttpURLConnection newDatabaseConnection = mock(HttpURLConnection.class);
		when(cf.createConnection("new_database")).thenReturn(newDatabaseConnection);
		when(newDatabaseConnection.getResponseCode()).thenReturn(404);
		
		HttpCouchConnection couchConnection = new HttpCouchConnection(cf);
		couchConnection.deleteDatabase("new_database");

	}
	
	@Test(expected=DatabaseNameException.class)
	public void deleteDatabaseWithIncompatableNameTest() throws IOException, ResponseException, ServerConnectionException, DatabaseNameException, DatabaseNotFoundException{
		HttpURLConnection newDatabaseConnection = mock(HttpURLConnection.class);
		when(cf.createConnection("@new_database")).thenReturn(newDatabaseConnection);
		when(newDatabaseConnection.getResponseCode()).thenReturn(400);
		
		HttpCouchConnection couchConnection = new HttpCouchConnection(cf);
		couchConnection.deleteDatabase("@new_database");

	}


}
