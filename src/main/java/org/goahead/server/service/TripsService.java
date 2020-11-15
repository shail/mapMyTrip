package org.goahead.server.service;

import org.goahead.server.core.pojos.Trip;
import org.goahead.server.dao.TripsDao;
import org.jdbi.v3.sqlobject.CreateSqlObject;

public abstract class TripsService {
  // Need this in order to reuse the same database connection
  @CreateSqlObject
  abstract TripsDao tripsDao();

  public Trip createTrip(Trip trip) {
    int id = (int) tripsDao().createTrip(trip);
    return tripsDao().getTrip(id);
  }

}
