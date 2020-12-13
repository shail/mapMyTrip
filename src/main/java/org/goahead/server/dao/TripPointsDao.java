package org.goahead.server.dao;

import java.util.List;
import org.goahead.server.core.pojos.TripPoint;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface TripPointsDao {
  @SqlQuery("select * from trip_points where trip_id = :tripId")
  public List<TripPoint> getTripPointsForTrip(@Bind("tripId") final int tripId);
}
