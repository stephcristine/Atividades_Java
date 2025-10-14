package br.edu.ifpr.tempconv.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import br.edu.ifpr.tempconv.model.Temperature;
import br.edu.ifpr.tempconv.utils.TemperatureUtils;

public enum TemperatureMongoRepository {
	INSTANCE;
	
	private final String MONGODB_CONN = "mongodb://localhost:28017";
	private final String TEMP_DB      = "temperature_db";
	private final String TEMP_COLL    = "temperatures";

	private MongoClient               mongoClient;
	private MongoDatabase             database;
	private MongoCollection<Document> coll;

	TemperatureMongoRepository() {
	   mongoClient = MongoClients.create(MONGODB_CONN);
	   database    = mongoClient.getDatabase(TEMP_DB);
	   coll        = database.getCollection(TEMP_COLL);
	}

	public boolean insert(Temperature temperature) {
		
		Long millis = TemperatureUtils.timestampToMillis(temperature.getTimestamp());
	    
	    String id = TemperatureUtils.timestampToString(millis);

	    Document doc = new Document("_id", id)
	                        .append("timestamp", temperature.getTimestamp().toString())
	                        .append("tempi", temperature.getTempi())
	                        .append("typei", temperature.getTypei().toString())
	                        .append("tempo", temperature.getTempo())
	                        .append("typeo", temperature.getTypeo().toString());

	    return coll.insertOne(doc).getInsertedId() != null;
	}
	
	public boolean update(Temperature temperature) {
		  Long millis = TemperatureUtils.timestampToMillis(temperature.getTimestamp());
	    
	      String id = TemperatureUtils.timestampToString(millis);

	      Bson filter           = Filters.eq("_id", id);
	      Bson updateOneUpdates = Updates.combine(
	                                     Updates.set("timestamp",temperature.getTimestamp().toString()),
	                                     Updates.set("tempi",temperature.getTempi()),
	                                     Updates.set("typei",temperature.getTypei().toString()),
	                                     Updates.set("tempo",temperature.getTempo()),
	    		  						 Updates.set("typeo",temperature.getTypeo().toString()));

	      return (coll.updateOne(filter,updateOneUpdates)).getModifiedCount() > 0L;
	   }

	public boolean delete(Temperature temperature) {
		Long millis = TemperatureUtils.timestampToMillis(temperature.getTimestamp());
	    String id = TemperatureUtils.timestampToString(millis);
	    return delete(id);
    }

	public boolean delete(String id) {
	    Bson filter = Filters.eq("_id", id);
	    return coll.deleteOne(filter).getDeletedCount() > 0L;
	}
	
	public int delete() {
	      long deleted = coll.countDocuments();

	      if (deleted > 0L)
	         coll.drop();

	      return (int) deleted;
	   }
	
	public List<Temperature> findAll() {
	    List<Temperature> temperatures = new ArrayList<>();
	    FindIterable<Document> docs = coll.find();

	    for (Document doc : docs) {
	        temperatures.add(TemperatureUtils.fromDocument(doc));
	    }

	    return temperatures;
	}

	public Optional<Temperature> findById(String id) {
	    Bson filter = Filters.eq("_id", id);
	    Document doc = coll.find(filter).first();

	    if (doc != null)
	        return Optional.of(TemperatureUtils.fromDocument(doc));

	    return Optional.empty();
	}

}
