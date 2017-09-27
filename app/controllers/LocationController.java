package controllers;

import dal.contexts.LocationMongoContext;
import dal.repositories.LocationRepository;
import models.Location;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;

import java.util.ArrayList;

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
        return ok(views.html.newlocationform.render(form));
    }

    //loads the page with available locations
    public Result loadAvalaibleLocations(){
        return ok(views.html.alllocations.render());
    }

    // method to get the results from the html form and redirect the user to the next page
    public Result createLocation(){
        final Form<Location> boundForm = form.bindFromRequest();

        if(boundForm.hasErrors()){
            play.Logger.ALogger logger = play.Logger.of(getClass());
            logger.error("errors ={}", boundForm.errors());
            return badRequest(views.html.newlocationform.render(boundForm));
        }
        else{
            Location data = boundForm.get();
            if(locationrepo.addLocation(data)){
                return redirect(routes.LocationController.loadLocationForm());
            }
            return redirect(routes.LocationController.loadLocationForm());

        }
    }
}
