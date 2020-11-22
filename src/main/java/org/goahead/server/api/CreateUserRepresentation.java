package org.goahead.server.api;

import com.google.common.base.Preconditions;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.goahead.server.core.pojos.User;

/**
 * Extends the user representation for a create user request since this will involve a password
 * which will not be sent normally
 */
public class CreateUserRepresentation extends UserRepresentation {
  private String password;

  public CreateUserRepresentation() {
    super();
  }

  public CreateUserRepresentation(User user) {
    super(user);
    this.password = user.getPasswordHash();
  }

  public CreateUserRepresentation(
      final Integer id,
      final String firstName,
      final String lastName,
      final int age,
      final String email,
      final String password) {
    super(id, firstName, lastName, age, email);
    Preconditions.checkArgument(StringUtils.isNotEmpty(password));
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    CreateUserRepresentation that = (CreateUserRepresentation) o;
    return password.equals(that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), password);
  }
}
