package org.goahead.server.auth;

import java.security.Principal;
import java.util.Objects;
import java.util.Set;

public class UserPrincipal implements Principal {
  private final String name;
  private final Set<String> roles;

  public UserPrincipal(String name) {
    this.name = name;
    this.roles = null;
  }

  public UserPrincipal(String name, Set<String> roles) {
    this.name = name;
    this.roles = roles;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return (int) (Math.random() * 100);
  }

  public Set<String> getRoles() {
    return roles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserPrincipal that = (UserPrincipal) o;
    return name.equals(that.name) &&
        roles.equals(that.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, roles);
  }
}
