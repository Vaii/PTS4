package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.TuitionFormContext;
import models.Training;
import models.TuitionForm;
import models.User;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

public class TuitionFormMongoContext implements TuitionFormContext {
    private DBConnector connector;
    private MongoCollection collection;

    public TuitionFormMongoContext(String coll) {
        connector = new DBConnector();
        collection = connector.getCollection(coll);
    }

    @Override
    public boolean addForm(TuitionForm form) {
        WriteResult result = collection.save(form);
        return result.wasAcknowledged();
    }

    @Override
    public boolean updateForm(TuitionForm form) {
        WriteResult result = collection.save(form);
        return result.wasAcknowledged();
    }

    @Override
    public boolean removeForm(TuitionForm form) {
        WriteResult result = collection.remove(new ObjectId(form.get_id()));
        return result.wasAcknowledged();
    }

    @Override
    public List<TuitionForm> getForm(User manager) {
        MongoCursor<TuitionForm> results = collection.find("{Manager:#}", manager).as(TuitionForm.class);
        List<TuitionForm> forms = new ArrayList<>();

        while(results.hasNext()) {
            TuitionForm form = results.next();
            forms.add(form);
        }
        return forms;
    }

    @Override
    public List<TuitionForm> getForms(User employee) {
        MongoCursor<TuitionForm> results = collection.find("{Employee:#}", employee).as(TuitionForm.class);
        List<TuitionForm> forms = new ArrayList<>();

        while(results.hasNext()) {
            TuitionForm form = results.next();
            forms.add(form);
        }
        return forms;
    }
}
