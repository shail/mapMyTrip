package org.goahead.server.dao;

import java.util.List;
import org.goahead.server.core.pojos.User;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UsersDao {
  @SqlQuery("select * from users;")
  public List<User> getUsers();

  @SqlQuery("select * from users where id = :id")
  public User getUser(@Bind("id") final int id);

  @SqlUpdate(
      "insert into users(first_name, last_name, age, email, password_hash, salt) values(:user.firstName,"
          + ":user.lastName, :user.age, :user.email, :user.passwordHash, :user.salt)")
  @GetGeneratedKeys("id")
  long createUser(@BindBean("user") final User user);

  @SqlUpdate(
      "update users set firstName = :user.firstName, lastName = :user.lastName, age = :user.age,"
          + "email = :user.email, password_hash = :user.passwordHash, salt = :user.salt where id"
          + " = :user.id")
  void updateUser(@BindBean("user") final User user);

  @SqlUpdate("delete from users where id = :id")
  boolean deleteUser(@Bind("id") final int id);
}
