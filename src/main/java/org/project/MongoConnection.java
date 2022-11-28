package org.project;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.internal.async.SingleResultCallback;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import static com.mongodb.client.model.Filters.eq;


public class MongoConnection {

  MongoConnection singleConnection = null;
  Window window;

  public MongoConnection (Window win) {
    window = win;
  }

  void connect (int score) {
    // you can use these credentials until there are any overwrite conflicts, then talk to Paul
    ConnectionString connectionString = new ConnectionString("mongodb+srv://student:comp2522fall2022student@bcit-comp2522-fall-2022.lf8mknm.mongodb.net/?retryWrites=true&w=majority");
    // settings for connecting to MongoDB
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .serverApi(ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build())
        .build();
    // connect to MongoDB
    MongoClient mongoClient = MongoClients.create(settings);
    // Use this database unless there are overwrite conflicts
    MongoDatabase database = mongoClient.getDatabase("comp2522fall2022");

    // Replace "test" with your group name
    MongoCollection<Document> collection = database.getCollection("HopperGame");

    // Create a document
    Document doc = new Document("gameScore", "" + score);

    // Async calls may execute in any order
    // insert document
    collection.insertOne(doc)
        // subscribe takes a class that defines the callback
        .subscribe(new SubscriberHelpers.OperationSubscriber<InsertOneResult>());
    // find document
    collection.find()
        // subscribe takes a class that defines the callback
        .subscribe(new SubscriberHelpers.PrintDocumentSubscriber(this.window));

    // TODO: I strongly suggest saving local copies of your BSON/JSON data.
    // Since this is a shared login, there is no guarantee that someone will not
    // overwrite your data. You can make your own free MongoDB Atlas instance as well.


  }


}
