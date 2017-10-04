package controllers;

import dal.contexts.LocationMongoContext;
import dal.repositories.LocationRepository;
import models.Location;
import models.Secured;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.html.location.*;
import play.mvc.*;
import javax.inject.Inject;

import java.util.ArrayList;

import static play.mvc.Http.Context.Implicit.ctx;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

public class LocationController {
    private final Form<Location> form;
    private ArrayList<Location>availablelocations;
    private LocationRepository locationrepo;

    @Inject
    public LocationController(FormFactory formFactory){
        this.form = formFactory.form(Location.class);
        locationrepo = new LocationRepository(new LocationMongoContext("Location"));
    }

    //Loads the generic form for adding a location
    public Result loadLocationForm(){
        return ok(newlocationform.render(form, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()) , "All Locations"));
    }


    // method to get the results from the html form and redirect the user to the next page
    public Result createLocation(){
    Form<Location> boundForm = form.bindFromRequest();
    Location data = boundForm.get();
    if(locationrepo.addLocation(data)){
        return redirect(routes.LocationController.locationOverview());
    }
    return redirect(routes.LocationController.loadLocationForm());

  }

    public Result locationOverview(){
        return ok(alllocations.render(locationrepo.getAll(),Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()) , "hallo"));
    }
}
