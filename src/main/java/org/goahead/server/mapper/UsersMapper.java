package org.goahead.server.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.goahead.server.api.LatLng;
import org.goahead.server.core.pojos.Trip;
import org.goahead.server.core.pojos.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class UsersMapper implements RowMapper<User> {
  @Override
  public User map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new User(
        rs.getInt("id"),
        rs.getString("first_name"),
        rs.getString("last_name"),
        rs.getInt("age"),
        rs.getString("email"),
        rs.getString("passwordHash"),
        rs.getString("salt")
    );
  }
}
