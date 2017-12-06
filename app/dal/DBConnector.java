package dal;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * Class used for establishing a connection with the mongo database.
 */

public final class DBConnector {
    private Jongo jongo;

    public DBConnector() {

        // DB Password in code, we'll need a way around this.
        MongoClientURI uri = new MongoClientURI(
                "mongodb://InfoSupport:InfoSupport123@ds163034.mlab.com:63034/infosupport");

        MongoClient mongoClient = new MongoClient(uri);

        // Hardcoded database name
        DB database = mongoClient.getDB("infosupport");

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
