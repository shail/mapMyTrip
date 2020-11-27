package org.goahead.server.auth;

import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.PrincipalImpl;

public class BasicAuthorizer implements Authorizer<PrincipalImpl> {
  @Override
  public boolean authorize(PrincipalImpl principal, String role) {
    return role.equalsIgnoreCase("admin");
  }
}
