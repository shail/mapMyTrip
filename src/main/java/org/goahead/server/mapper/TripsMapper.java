package org.goahead.server.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.goahead.server.api.LatLng;
import org.goahead.server.api.Trip;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class TripsMapper implements RowMapper<Trip> {
  @Override
  public Trip map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new Trip(
        rs.getInt("id"),
        rs.getString("name"),
        new LatLng(rs.getDouble("lat"), rs.getDouble("lng")));
  }
}
