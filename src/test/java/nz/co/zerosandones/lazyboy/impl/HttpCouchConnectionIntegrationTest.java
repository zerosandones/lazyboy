package nz.co.zerosandones.lazyboy.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import nz.co.zerosandones.lazyboy.DatabaseExistsException;
import nz.co.zerosandones.lazyboy.DatabaseNameException;
import nz.co.zerosandones.lazyboy.IntegrationTest;
import nz.co.zerosandones.lazyboy.ResponseException;
import nz.co.zerosandones.lazyboy.ServerConnectionException;

/*
 * Need some way to set the address of the couchDB instance without hard coding it.
 */
@Category(IntegrationTest.class)
public class HttpCouchConnectionIntegrationTest {
	
	private URL couchDBServerAddress;
	private HttpConnectionFactory cf;
	
	@BeforeClass
	public static void clearPreviousData() throws IOException{
		URL serverAddress = new URL("http://192.168.1.6:5984");
		
		URL deleteDatabase = new URL(serverAddress, "new_database");
		HttpURLConnection connection = (HttpURLConnection)deleteDatabase.openConnection();
		connection.setRequestMethod("DELETE");
		connection.getResponseCode();
		
		deleteDatabase = new URL(serverAddress, "new_database_that_exists");
		connection = (HttpURLConnection)deleteDatabase.openConnection();
		connection.setRequestMethod("DELETE");
		connection.getResponseCode();
		
	}
	
	
	@Before
	public void init() throws MalformedURLException{
		couchDBServerAddress = new URL("http://192.168.1.6:5984/");
		cf = new HttpConnectionFactory(couchDBServerAddress);
	}

	@Test
	public void createDatabaseTest() throws ResponseException, ServerConnectionException, IOException, DatabaseNameException, DatabaseExistsException {
		HttpCouchConnection couchConnection = new HttpCouchConnection(cf);
		couchConnection.createDatabase("new_database");
		
		URL deleteDatabase = new URL(couchDBServerAddress, "new_database");
		HttpURLConnection connection = (HttpURLConnection)deleteDatabase.openConnection();
		connection.setRequestMethod("HEAD");
		int responseCode = connection.getResponseCode();
		assertEquals(200, responseCode);
	}
	
	@Test(expected=DatabaseExistsException.class)
	public void createDatabaseThatExistsTest() throws ResponseException, ServerConnectionException, IOException, DatabaseNameException, DatabaseExistsException {
		HttpCouchConnection couchConnection = new HttpCouchConnection(cf);
		couchConnection.createDatabase("new_database_that_exists");
		couchConnection.createDatabase("new_database_that_exists");
	}
	
	@Test(expected=DatabaseNameException.class)
	public void createDatabasewithInvalidNameTest() throws ResponseException, ServerConnectionException, IOException, DatabaseNameException, DatabaseExistsException {
		HttpCouchConnection couchConnection = new HttpCouchConnection(cf);
		couchConnection.createDatabase("@new_database");
	}

}
