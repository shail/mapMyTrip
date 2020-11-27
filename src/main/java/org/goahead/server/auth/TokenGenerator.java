package org.goahead.server.auth;

import io.dropwizard.auth.PrincipalImpl;

public interface TokenGenerator {
  String getToken(PrincipalImpl user);
}
