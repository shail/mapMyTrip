package org.goahead.server.auth;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import java.util.Optional;
import org.goahead.server.core.pojos.User;
import org.goahead.server.service.UsersService;
import org.mindrot.jbcrypt.BCrypt;

public class BasicAuthenticator implements Authenticator<BasicCredentials, UserPrincipal> {
  private final UsersService usersService;

  public BasicAuthenticator(UsersService usersService) {
    this.usersService = usersService;
  }

  @Override
  public Optional<UserPrincipal> authenticate(BasicCredentials basicCredentials) {
    User user = usersService.getUser(basicCredentials.getUsername());
    if (user == null) {
      return Optional.empty();
    }
    if (BCrypt.checkpw(basicCredentials.getPassword(), user.getPasswordHash())) {
      return Optional.of(new UserPrincipal(basicCredentials.getUsername()));
    }
    return Optional.empty();
  }
}
