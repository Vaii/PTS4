package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import models.storage.Category;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;


public class CategoryContext implements dal.interfaces.CategoryContext {
    private DBConnector connector;
    private MongoCollection collection;

    public CategoryContext(String coll){
        connector = new DBConnector();
        collection = connector.getCollection(coll);
    }

    @Override
    public List<Category> getAllCategories() {
        MongoCursor<Category> results = collection.find().as(Category.class);
        List<Category> categories = new ArrayList<>();

        while(results.hasNext()) {
            Category category = results.next();
            categories.add(category);
        }
        return categories;
    }

    @Override
    public Boolean addCategory(Category category) {
        WriteResult result = collection.save(category);
        return result.wasAcknowledged();
    }

    @Override
    public Category getCategoryById(String categoryId) {
        return collection.findOne("{_id:#}", new ObjectId(categoryId)).as(Category.class);
    }
}
