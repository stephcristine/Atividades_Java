package br.edu.ifpr.tempconv.repository;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.edu.ifpr.tempconv.model.Temperature;

public enum TemperatureRepo {
	INSTANCE;
	
	private final String MONGODB_CONN = "mongodb://localhost:28017";
	private final String TEMP_DB      = "temperature_db";
	private final String TEMP_COLL    = "temperatures";

	private MongoClient               mongoClient;
	private MongoDatabase             database;
	private MongoCollection<Document> coll;
	
	TemperatureRepo() {
	   mongoClient = MongoClients.create(MONGODB_CONN);
	   database    = mongoClient.getDatabase(TEMP_DB);
	   coll        = database.getCollection(TEMP_COLL);
	}
	
	public boolean insert(Temperature temperature) {
	      Document doc = new Document("timestamp",temperature.getTimestamp())
	                          .append("tempi",temperature.getTempi())
	                          .append("typei",temperature.getTypei())
	                          .append("tempo",temperature.getTempo())
	                          .append("typeo",temperature.getTypeo());
	                          

	      return coll.insertOne(doc).getInsertedId() != null;
	   }
}
