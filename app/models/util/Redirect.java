package models.util;


import play.Logger;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

public class Redirect extends play.mvc.Action.Simple {
    String previousUrl = "";

    public CompletionStage<Result> call(Http.Context ctx) {
        Logger.info("Calling action for {}", ctx);
        storePreviousUrl(ctx);
        return delegate.call(ctx);
    }

    private void storePreviousUrl(Http.Context ctx) {
        System.out.println("Storing Url: " + ctx.request().uri());
        ctx.session().put("previousUrl", ctx.request().uri());
        previousUrl = ctx.request().uri();
    }

    public String getPreviousUrl() {
        return previousUrl;
    }
}
