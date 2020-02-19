package com.malsoor;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

/**
 * Java + MongoDB Hello world Example
 * 
 */
public class Application {
	public static void main(String[] args) {

		try {

			/**** Connect to MongoDB ****/
			// Since 2.10.0, uses MongoClient
			MongoClient mongo = new MongoClient("localhost", 27017);

            // or, to connect to a replica set, with auto-discovery of the primary -
            // Please check in your case, there might be prd db replica configuration
            MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
                    new ServerAddress("localhost", 27018),
                    new ServerAddress("localhost", 27019)));


			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("testdb");

			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("summary");

			/**** Insert ****/
			// create a document to store key and value
            // this is customObject of type record which we are trying to stor/fetch
			BasicDBObject document = new BasicDBObject();
			document.put("name", "malsoor");
			document.put("age", 30);
			document.put("createdDate", new Date());
			table.insert(document);

			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("name", "malsoor");

			DBCursor cursor = table.find(searchQuery);

			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}

			/**** Update ****/
			// search document where name="malsoor" and update it with new values
			BasicDBObject query = new BasicDBObject();
			query.put("name", "malsoor");

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("name", "malsoor-updated");

			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);

			table.update(query, updateObj);

			/**** Find and display ****/
			BasicDBObject searchQuery2 
				= new BasicDBObject().append("name", "malsoor-updated");

			DBCursor cursor2 = table.find(searchQuery2);

			while (cursor2.hasNext()) {
				System.out.println(cursor2.next());
			}

			/**** Done ****/
			System.out.println("Done");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
}
