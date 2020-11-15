package org.goahead.server.service;

import org.goahead.server.dao.UsersDao;

public abstract class UsersService {
  abstract UsersDao usersDao();
}
