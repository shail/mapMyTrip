package org.goahead.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAuthResponse {
  @JsonProperty("token")
  private final String token;

  public UserAuthResponse(String token) {
    this.token = token;
  }
}

