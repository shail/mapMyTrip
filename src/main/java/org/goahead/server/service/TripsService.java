package org.goahead.server.service;

import java.util.List;
import java.util.Objects;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import org.goahead.server.api.TripRepresentation;
import org.goahead.server.core.pojos.Trip;
import org.goahead.server.dao.TripsDao;
import org.jdbi.v3.sqlobject.CreateSqlObject;

/**
 * This TripsService class will be used to access the TripsDao. This ensures that if anything about
 * the underlying database/query methods change, we won't have to change any of the client code.
 */
public interface TripsService {
  // Need this in order to reuse the same database connection
  @CreateSqlObject
  TripsDao tripsDao();

  default Trip createTrip(final TripRepresentation tripRepresentation) {
    int id = (int) tripsDao().createTrip(new Trip(tripRepresentation));
    return tripsDao().getTrip(id);
  }

  default List<Trip> getTrips() {
    return tripsDao().getTrips();
  }

  default Trip getTrip(final int id) {
    return tripsDao().getTrip(id);
  }

  default Trip updateTrip(final TripRepresentation trip) {
    if (Objects.isNull(tripsDao().getTrip(trip.getId()))) {
      throw new WebApplicationException(
          String.format("No trip with id: %d found", trip.getId()), Status.NOT_FOUND);
    }
    tripsDao().updateTrip(new Trip(trip));
    return tripsDao().getTrip(trip.getId());
  }

  default boolean deleteTrip(final int id) {
    boolean tripDeleted = tripsDao().deleteTrip(id);
    if (!tripDeleted) {
      throw new WebApplicationException(
          String.format("No trip with id: %d found", id), Status.NOT_FOUND);
    }
    return true;
  }
}
