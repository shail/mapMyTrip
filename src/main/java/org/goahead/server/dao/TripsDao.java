package org.goahead.server.dao;

import java.util.List;
import org.goahead.server.core.pojos.Trip;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TripsDao {
  @SqlQuery("select * from trips;")
  public List<Trip> getTrips();

  @SqlQuery("select * from trips where id = :id")
  public Trip getTrip(@Bind("id") final int id);

  @SqlUpdate(
      "insert into trips(name, lat, lng) values(:trip.name, :trip.latLng.lat, :trip.latLng.lng)")
  @GetGeneratedKeys("id")
  long createTrip(@BindBean("trip") final Trip trip);

  @SqlUpdate(
      "update trips set name = :trip.name, lat = :trip.latLng.lat, lng = :trip.latLng.lng where id"
          + " = :trip.id")
  void updateTrip(@BindBean("trip") final Trip trip);

  @SqlUpdate("delete from trips where id = :id")
  boolean deleteTrip(@Bind("id") final int id);
}
