package org.goahead.server.auth;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGeneratorUtil {

  private static String generateRawSecret(int length) {
    byte[] buf = new byte[length];
    new SecureRandom().nextBytes(buf);
    String rawSecret = Base64.getEncoder().encodeToString(buf);
    return rawSecret.substring(1, length + 1);
  }

  public static void main(String[] args) {
    System.out.println(generateRawSecret(32));
  }
}
