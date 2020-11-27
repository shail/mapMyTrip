package org.goahead.server.auth;

public interface TokenGenerator {
  String getToken(UserPrincipal user);
}
