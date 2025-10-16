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
import br.edu.ifpr.tempconv.utils.TemperatureConverter;
import br.edu.ifpr.tempconv.utils.TemperatureUtils;

public enum TemperatureMongoRepository {
	INSTANCE;
	
	private final String MONGODB_CONN = "mongodb://localhost:27017";
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

	public Document insert(Temperature temperature) {
	    Long millis = TemperatureUtils.timestampToMillis(temperature.getTimestamp());
	    String id = TemperatureUtils.timestampToString(millis);

	    Document doc = new Document("_id", id)
	                        .append("tempi", temperature.getTempi())
	                        .append("typei", temperature.getTypei().toString())
	                        .append("tempo", temperature.getTempo())
	                        .append("typeo", temperature.getTypeo().toString());

	    coll.insertOne(doc);
	    return doc;
	}
	
	public Optional<Document> update(String id, Temperature temperature) {
	    Bson filter = Filters.eq("_id", id);
	    
	    double tempo = TemperatureConverter.calculateTempOutput(
	            temperature.getTypei(),
	            temperature.getTempi(),
	            temperature.getTypeo()
	        );
	    
	    Bson updates = Updates.combine(
	        Updates.set("tempi", temperature.getTempi()),
	        Updates.set("typei", temperature.getTypei().toString()),
	        Updates.set("tempo", tempo),
	        Updates.set("typeo", temperature.getTypeo().toString())
	    );	

	    long modifiedCount = coll.updateOne(filter, updates).getModifiedCount();

	    if (modifiedCount > 0) {
	        Document updatedDoc = coll.find(filter).first();
	        return Optional.ofNullable(updatedDoc);
	    } else {
	        return Optional.empty();
	    }
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
	
	public List<Document> findAll() {
	    List<Document> docs = new ArrayList<>();
	    coll.find().forEach(docs::add);
	    return docs;
	}

	public Document findById(String id) {
	    Bson filter = Filters.eq("_id", id);
	    return coll.find(filter).first();
	}
	
	public List<Document> findByTypei(String typei) {
	    List<Document> temperatures = new ArrayList<>();

	    FindIterable<Document> docs = coll.find(Filters.eq("typei", typei));

	    for (Document doc : docs) {
	        temperatures.add(doc);
	    }

	    return temperatures;
	}
	
	public List<Document> findByTypeo(String typeo) {
	    List<Document> temperatures = new ArrayList<>();

	    FindIterable<Document> docs = coll.find(Filters.eq("typeo", typeo));

	    for (Document doc : docs) {
	        temperatures.add(doc);
	    }

	    return temperatures;
	}

}
