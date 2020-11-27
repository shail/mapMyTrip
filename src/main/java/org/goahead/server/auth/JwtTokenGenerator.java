package org.goahead.server.auth;

import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

import io.dropwizard.auth.PrincipalImpl;
import org.goahead.server.core.pojos.User;
import org.goahead.server.service.UsersService;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

public class JwtTokenGenerator implements TokenGenerator {
  private final UsersService usersService;
  public JwtTokenGenerator(UsersService usersService) {
    this.usersService = usersService;
  }

  @Override
  public String getToken(PrincipalImpl user) {
    User fullUser = usersService.getUser(user.getName());
    final JwtClaims claims = new JwtClaims();
    claims.setSubject(user.getName());
    claims.setStringClaim("user", user.getName());
    claims.setIssuedAtToNow();
    claims.setGeneratedJwtId();

    final JsonWebSignature jws = new JsonWebSignature();
    jws.setPayload(claims.toJson());
    jws.setAlgorithmHeaderValue(HMAC_SHA256);
    jws.setKey(new HmacKey("dfwzsdzwh823zebdwdz772632gdsbd3333".getBytes()));
    try {
      return jws.getCompactSerialization();
    } catch (JoseException ex) {
      throw new RuntimeException(ex);
    }
  }
}
