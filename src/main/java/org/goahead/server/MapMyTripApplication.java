package org.goahead.server;

import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.goahead.server.api.Trip;
import org.goahead.server.dao.TripsDao;
import org.goahead.server.mapper.TripsMapper;
import org.goahead.server.resources.TripsResource;
import org.jdbi.v3.core.Jdbi;

public class MapMyTripApplication extends Application<MapMyTripConfiguration> {

  public static void main(final String[] args) throws Exception {
    new MapMyTripApplication().run(args);
  }

  @Override
  public String getName() {
    return "mapMyTrip";
  }

  @Override
  public void initialize(final Bootstrap<MapMyTripConfiguration> bootstrap) {
    bootstrap.addBundle(new JdbiExceptionsBundle());
    // TODO: application initialization
  }

  @Override
  public void run(final MapMyTripConfiguration configuration, final Environment environment) {
    final JdbiFactory databaseFactory = new JdbiFactory();
    final Jdbi jdbi =
        databaseFactory.build(environment, configuration.getDataSourceFactory(), "mysql");
    final TripsDao tripsDao = jdbi.onDemand(TripsDao.class);
    jdbi.registerRowMapper(Trip.class, new TripsMapper());
    // TODO: implement application
    environment.jersey().register(new TripsResource(tripsDao));
  }
}
