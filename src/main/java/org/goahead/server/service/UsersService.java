package org.goahead.server.service;

import java.util.List;
import java.util.Objects;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import org.goahead.server.api.CreateUserRepresentation;
import org.goahead.server.api.UserRepresentation;
import org.goahead.server.core.pojos.User;
import org.goahead.server.dao.UsersDao;
import org.jdbi.v3.sqlobject.CreateSqlObject;

public interface UsersService {
  @CreateSqlObject
  UsersDao usersDao();

  default User createUser(final CreateUserRepresentation userRepresentation) {
    int id = (int) usersDao().createUser(new User(userRepresentation));
    return usersDao().getUser(id);
  }

  default List<User> getUsers() {
    return usersDao().getUsers();
  }

  default User getUser(final int id) {
    return usersDao().getUser(id);
  }

  default User updateUser(final UserRepresentation user) {
    User userToUpdate = usersDao().getUser(user.getId());
    if (Objects.isNull(userToUpdate)) {
      throw new WebApplicationException(
          String.format("No user with id: %d found", user.getId()), Status.NOT_FOUND);
    }

    usersDao().updateUser(new User(user, userToUpdate));
    return usersDao().getUser(user.getId());
  }

  default boolean deleteUser(final int id) {
    boolean tripDeleted = usersDao().deleteUser(id);
    if (!tripDeleted) {
      throw new WebApplicationException(
          String.format("No user with id: %d found", id), Status.NOT_FOUND);
    }
    return true;
  }
}
