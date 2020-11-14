package org.goahead.server.dao;

import java.util.List;
import org.goahead.server.api.Trip;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TripsDao {
  @SqlQuery("select * from trips;")
  public List<Trip> getTrips();

  @SqlUpdate(
      "insert into trips(name, lat, lng) values(:trip.name, :trip.latLng.lat, :trip.latLng.lng)")
  @GetGeneratedKeys("id")
  long createTrip(@BindBean("trip") final Trip trip);
}
