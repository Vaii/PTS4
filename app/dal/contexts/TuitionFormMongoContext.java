package dal.contexts;

import com.mongodb.WriteResult;
import dal.DBConnector;
import dal.interfaces.TuitionFormContext;
import models.storage.TuitionForm;
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
        WriteResult result = collection.update("{_id:#}", new ObjectId(form.getId())).with("{manager:#," +
                        " employee:#," +
                        " training:#," +
                        " companyJoinDate:#," +
                        " reasonForCourse:#," +
                        " studyCosts:#," +
                        " examinationFees:#," +
                        " transportCosts:#," +
                        " accommodationCosts:#," +
                        " extraCosts:#," +
                        " totalCosts:#," +
                        " category:#," +
                        " status:#," +
                        " dateTimeID:#," +
                        " employeeID:#}", form.getManager(),
        form.getEmployee(), form.getTraining(), form.getCompanyJoinDate(), form.getReasonForCourse(), form.getStudyCosts(),
        form.getExaminationFees(), form.getTransportCosts(), form.getAccommodationCosts(), form.getExtraCosts(), form.getTotalCosts(),
        form.getCategory(), form.getStatus(), form.getDateTimeID(), form.getEmployeeID());

        return result.wasAcknowledged();
    }

    @Override
    public boolean removeForm(TuitionForm form) {
        WriteResult result = collection.remove(new ObjectId(form.getId()));
        return result.wasAcknowledged();
    }

    @Override
    public List<TuitionForm> getForm(String managerID) {
        MongoCursor<TuitionForm> results = collection.find("{manager:#}", managerID).as(TuitionForm.class);
        List<TuitionForm> forms = new ArrayList<>();

        while(results.hasNext()) {
            TuitionForm form = results.next();
            forms.add(form);
        }
        return forms;
    }

    @Override
    public List<TuitionForm> getForms(String employeeID) {
        MongoCursor<TuitionForm> results = collection.find("{employeeID:#}", employeeID).as(TuitionForm.class);
        List<TuitionForm> forms = new ArrayList<>();

        while(results.hasNext()) {
            TuitionForm form = results.next();
            forms.add(form);
        }
        return forms;
    }

    @Override
    public TuitionForm getSpecific(String tuitionID) {

        return collection.findOne("{_id:#}", new ObjectId(tuitionID)).as(TuitionForm.class);
    }
}
