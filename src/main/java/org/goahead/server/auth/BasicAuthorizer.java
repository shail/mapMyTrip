package org.goahead.server.auth;

import io.dropwizard.auth.Authorizer;

public class BasicAuthorizer implements Authorizer<UserPrincipal> {
  @Override
  public boolean authorize(UserPrincipal principal, String role) {
    return role.equalsIgnoreCase("admin");
  }
}
