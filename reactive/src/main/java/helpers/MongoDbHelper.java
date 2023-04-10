package helpers;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.Document;
import rx.Observable;

public class MongoDbHelper {
    private final MongoDatabase database;

    public MongoDbHelper(MongoDatabase database) {
        this.database = database;
    }

    public Observable<Document> findById(String collection, String id) {
        return database.getCollection(collection)
                .find(Filters.eq("id", id))
                .toObservable();
    }

    public Observable<Boolean> insertOneTo(String collection, Document document) {
        return database.getCollection(collection).insertOne(document).asObservable().isEmpty().map(x -> !x);
    }

    public Observable<Document> getAll(String collection) {
        return database.getCollection(collection)
                .find()
                .toObservable();
    }
}
