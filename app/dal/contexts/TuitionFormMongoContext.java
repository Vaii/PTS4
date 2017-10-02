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
    public boolean addTuitionForm(TuitionForm tuitionForm) {
        WriteResult result = collection.save(tuitionForm);
        return result.wasAcknowledged();
    }

    @Override
    public boolean updateTuitionForm(TuitionForm tuitionForm) {
        WriteResult result = collection.save(tuitionForm);
        return result.wasAcknowledged();
    }

    @Override
    public boolean removeTuitionForm(TuitionForm tuitionForm) {
        WriteResult result = collection.remove(new ObjectId(tuitionForm.get_id()));
        return result.wasAcknowledged();
    }

    @Override
    public List<TuitionForm> getAll() {
        MongoCursor<TuitionForm> results = collection.find().as(TuitionForm.class);
        List<TuitionForm> forms = new ArrayList<>();

        while(results.hasNext()) {
            TuitionForm form = results.next();
            forms.add(form);
        }
        return forms;
    }

    @Override
    public List<TuitionForm> getTuitionForm(User employees) {
        MongoCursor<TuitionForm> results = collection.find("{employee:#}", employees).as(TuitionForm.class);
        List<TuitionForm> forms = new ArrayList<>();

        while(results.hasNext()) {
            TuitionForm form = results.next();
            forms.add(form);
        }
        return forms;
    }

    @Override
    public List<TuitionForm> getTuitionForm(User manager, User Employee) {
        MongoCursor<TuitionForm> results = collection.find("{Employee:#, Manager:#}", Employee, manager).as(TuitionForm.class);
        List<TuitionForm> forms = new ArrayList<>();

        while(results.hasNext()) {
            TuitionForm form = results.next();
            forms.add(form);
        }
        return forms;
    }

    @Override
    public List<TuitionForm> getTuitionForm(Training training) {
        MongoCursor<TuitionForm> results = collection.find("{Training:#}", training).as(TuitionForm.class);
        List<TuitionForm> forms = new ArrayList<>();

        while(results.hasNext()) {
            TuitionForm form = results.next();
            forms.add(form);
        }
        return forms;
    }
}
