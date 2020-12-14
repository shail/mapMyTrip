package org.goahead.server.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.goahead.server.api.LatLng;
import org.goahead.server.core.pojos.TripPoint;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class TripPointsMapper implements RowMapper<TripPoint> {

  @Override
  public TripPoint map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new TripPoint(
        rs.getInt("id"),
        rs.getInt("trip_id"),
        new LatLng(rs.getDouble("lat"), rs.getDouble("lng")),
        rs.getLong("timestamp"));
  }
}
