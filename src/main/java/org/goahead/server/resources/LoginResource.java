package org.goahead.server.resources;

import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

import io.dropwizard.auth.Auth;
import io.dropwizard.auth.PrincipalImpl;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.goahead.server.api.UserAuthResponse;
import org.goahead.server.auth.TokenGenerator;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

  private final TokenGenerator tokenGenerator;

  public LoginResource(TokenGenerator tokenGenerator) {
    this.tokenGenerator = tokenGenerator;
  }

  //TODO: maybe this should be a POST, need to check
  @GET
  public final UserAuthResponse getToken(@Auth PrincipalImpl user) {
    return new UserAuthResponse(tokenGenerator.getToken(user));
  }
}


