package dal;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * Class used for establishing a connection with the mongo database.
 */

public class DBConnector {
    static private DB database;
    private Jongo jongo;

    public DBConnector() {

        // DB Password in code, we'll need a way around this.
        MongoClientURI uri = new MongoClientURI(
                "mongodb://InfoSupport:InfoSupport123@cluster0-shard-00-00-vx027.mongodb.net:27017,cluster0-shard-00-01-vx027.mongodb.net:27017,cluster0-shard-00-02-vx027.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");

        MongoClient mongoClient = new MongoClient(uri);

        // Hardcoded database name
        // TODO Make into variable
        database = mongoClient.getDB("InfoSupport");

        jongo = new Jongo(database);
    }

    /**
     * If the collection does not exist a new one with the given name will be created.
     * @param collecion The name of the collection.
     * @return The given Collection.
     */
    public MongoCollection getCollection(String collecion) {
        return jongo.getCollection(collecion);
    }
}
