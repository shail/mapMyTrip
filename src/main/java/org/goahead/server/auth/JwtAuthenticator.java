package org.goahead.server.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.PrincipalImpl;
import java.util.Optional;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.JwtContext;

public class JwtAuthenticator implements Authenticator<JwtContext, PrincipalImpl> {

  @Override
  public Optional<PrincipalImpl> authenticate(JwtContext jwtContext)
      throws AuthenticationException {
    try {
      JwtClaims claims = jwtContext.getJwtClaims();
      String userName = claims.getClaimValue("user", String.class);
      return Optional.of(new PrincipalImpl(userName));
    } catch (MalformedClaimException ex) {
      return Optional.empty();
    }
  }
}
