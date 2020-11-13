package org.goahead.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class mapMyTripApplication extends Application<mapMyTripConfiguration> {

    public static void main(final String[] args) throws Exception {
        new mapMyTripApplication().run(args);
    }

    @Override
    public String getName() {
        return "mapMyTrip";
    }

    @Override
    public void initialize(final Bootstrap<mapMyTripConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final mapMyTripConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
