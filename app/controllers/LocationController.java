package controllers;

/**
 * Created by Ken on 27-9-2017.
 */
public class LocationController {
<<<<<<< HEAD
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
=======
>>>>>>> parent of fa1523e... added function to add new locations
}
