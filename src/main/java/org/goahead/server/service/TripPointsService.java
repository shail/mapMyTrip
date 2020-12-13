package org.goahead.server.service;

import java.util.List;
import org.goahead.server.core.pojos.TripPoint;
import org.goahead.server.dao.TripPointsDao;
import org.jdbi.v3.sqlobject.CreateSqlObject;

public interface TripPointsService {
  @CreateSqlObject
  TripPointsDao tripPointsDao();

  default List<TripPoint> getTripPointsForTrip(final int id) {
    return tripPointsDao().getTripPointsForTrip(id);
  }
}
