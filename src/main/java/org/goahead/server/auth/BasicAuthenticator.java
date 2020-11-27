package org.goahead.server.auth;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentials;
import java.util.Optional;
import org.goahead.server.core.pojos.User;
import org.goahead.server.service.UsersService;
import org.mindrot.jbcrypt.BCrypt;

public class BasicAuthenticator implements Authenticator<BasicCredentials, PrincipalImpl> {
  private final UsersService usersService;

  public BasicAuthenticator(UsersService usersService) {
    this.usersService = usersService;
  }

  @Override
  public Optional<PrincipalImpl> authenticate(BasicCredentials basicCredentials) {
    User user = usersService.getUser(basicCredentials.getUsername());
    if (BCrypt.checkpw(basicCredentials.getPassword(), user.getPasswordHash())) {
      return Optional.of(new PrincipalImpl(basicCredentials.getUsername()));
    }
    return Optional.empty();
  }
}
