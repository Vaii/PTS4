package controllers;

import dal.contexts.DateTimeMongoContext;
import dal.contexts.LocationMongoContext;
import dal.repositories.DateTimeRepository;
import dal.repositories.LocationRepository;
import models.storage.DateTime;
import models.storage.Location;
import models.storage.Secured;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.html.location.*;
import play.mvc.*;
import javax.inject.Inject;
import java.util.List;

public class LocationController extends Controller {

    private final Form<Location> form;
    private LocationRepository locationRepo;
    private DateTimeRepository dateTimeRepo;

    @Inject
    public LocationController(FormFactory formFactory){
        this.form = formFactory.form(Location.class);
        locationRepo = new LocationRepository(new LocationMongoContext("Location"));
        dateTimeRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
    }

    //Loads the generic form for adding a location
    @Security.Authenticated(Secured.class)
    public Result loadLocationForm(){
        return ok(newlocationform.render(form, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()) , "Location Form"));
    }

    @Security.Authenticated(Secured.class)
    public Result locationOverview(){
        return ok(alllocations.render(locationRepo.getAll(),Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()) , "All Locations"));
    }

    @Security.Authenticated(Secured.class)
    public Result alterLocationForm(String id){
        if(id != null){
            Location location = locationRepo.getLocation(id);
            if(location != null){
                return ok(alterlocation.render(form, location, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Alter Location Form"));
            }
        }
        return locationOverview();

    }

    // method to get the results from the html form and redirect the user to the next page
    @Security.Authenticated(Secured.class)
    public Result createLocation(){
    Form<Location> boundForm = form.bindFromRequest();
    Location data = boundForm.get();
    if(locationRepo.addLocation(data)){
        return redirect(routes.LocationController.locationOverview());
    }
    return redirect(routes.LocationController.loadLocationForm());
  }

  @Security.Authenticated(Secured.class)
  public Result submitAlterLocation(){
        Form<Location> boundForm = form.bindFromRequest();
        Location data = boundForm.get();
        String locationId = boundForm.field("_id").value();
        if(locationRepo.updateLocation(locationId, data)){
            return redirect(routes.LocationController.locationOverview());
        }
        return redirect(routes.LocationController.locationOverview());
  }

  @Security.Authenticated(Secured.class)
  public Result deleteLocation(String locationId){

      List<DateTime> overlap = dateTimeRepo.getDateTimeForLocation(locationId);

      if(!overlap.isEmpty()) {
        return ok(locationError.render(overlap, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Alter Location Form"));
      }

      if(locationRepo.removeLocation(locationId)){
          return redirect(routes.LocationController.locationOverview());
      }
      return redirect(routes.LocationController.locationOverview());
  }

}
