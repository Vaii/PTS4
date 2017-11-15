package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.SupplierContext;
import models.storage.Supplier;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

public class SupplierMongoContext implements SupplierContext{

    private DBConnector connector;
    private MongoCollection collection;

    public SupplierMongoContext(String coll) {
        connector = new DBConnector();
        collection = connector.getCollection(coll);
    }


    @Override
    public boolean addSupplier(Supplier supplier) {
        WriteResult result = collection.save(supplier);
        return result.wasAcknowledged();
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        WriteResult result = collection.save(supplier);
        return result.wasAcknowledged();
    }

    @Override
    public boolean removeSupplier(Supplier supplier) {
        WriteResult result = collection.remove(new ObjectId(supplier.getId()));
        return result.wasAcknowledged();
    }

    @Override
    public Supplier getSupplier(String name) {
        return collection.findOne("{Name:#}", name).as(Supplier.class);
    }

    @Override
    public List<Supplier> getAll() {
        MongoCursor<Supplier> results = collection.find().as(Supplier.class);
        List<Supplier> suppliers = new ArrayList<>();

        while(results.hasNext()) {
            Supplier supplier = results.next();
            suppliers.add(supplier);
        }
        return suppliers;
    }
}
