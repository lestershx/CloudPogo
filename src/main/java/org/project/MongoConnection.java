package org.project;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;
import io.github.cdimascio.dotenv.Dotenv;

//import static com.mongodb.client.model.Filters.eq;

/**
 * MongoConnection is a singleton object that is configured to connect to a MongoDB database.
 *
 * @author Paul Bucci, modified by Buck Sin
 * @version 1.0
 */
public class MongoConnection {

  private static MongoConnection instance;
  Window window;

  /**
   * Returns a single instance of MongoConnection to ensure there will only be one object
   * at a time (Singleton).
   *
   * @param window PApplet Window that contains this object.
   * @return MongoConnection as a singleton object.
   */
  public static MongoConnection getInstance(Window window) {
    if (instance == null) {
      instance = new MongoConnection(window);
    }
    return instance;
  }

  /**
   * Constructor for a MongoConnection object.
   *
   * @param win PApplet Window that contains this object.
   */
  private MongoConnection(Window win) {
    window = win;
  }

  /**
   * Connect will upload the existing gamescore to a specified MongoDB database. It will also
   * retrieve the full list of scores from the database.
   *
   * @param score the gamescore to be uploaded to the database
   */
  void connect(int score) {
//    Dotenv dotenv = Dotenv.load();
//    String uri = dotenv.get("MONGO_URI");
    ConnectionString connectionString = new ConnectionString("mongodb+srv://lestershunnn_db_user:h0CECHG8GTiGXzrs@cluster0.eg7evd4.mongodb.net/?appName=Cluster0");
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
    MongoDatabase database = mongoClient.getDatabase("cloudhopper");

    // Replace "test" with your group name
    MongoCollection<Document> collection = database.getCollection("scores");

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
        .subscribe(new SubscriberHelpers.ParseDocumentSubscriber(this.window));
  }


}
