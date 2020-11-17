package org.goahead.server.api;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

/**
 * Extends the user representation for a create user request since this will involve a password
 * which will not be sent normally
 */
public class CreateUserRepresentation extends UserRepresentation {
  private String password;

  public CreateUserRepresentation() {
    super();
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
}
