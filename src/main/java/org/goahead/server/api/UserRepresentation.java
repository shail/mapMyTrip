package org.goahead.server.api;

import com.google.common.base.Preconditions;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import org.apache.commons.lang3.StringUtils;

/**
 * Simple User request/response object
 */
public class UserRepresentation {
  @Nullable
  private Integer id;
  @NotEmpty private String firstName;
  @NotEmpty private String lastName;
  private int age;
  @NotEmpty private String email;
  @Nullable private String password;

  public UserRepresentation() {

  }

  public UserRepresentation(final Integer id, final String firstName, final String lastName, final int age,
      final String email, final String password) {
    Preconditions.checkArgument(StringUtils.isNotEmpty(firstName));
    Preconditions.checkArgument(StringUtils.isNotEmpty(lastName));
    Preconditions.checkArgument(StringUtils.isNotEmpty(email));
    Preconditions.checkArgument(StringUtils.isNotEmpty(password));
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.email = email;
    this.password = password;
  }

  @Nullable
  public Integer getId() {
    return id;
  }

  public void setId(@Nullable Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Nullable
  public String getPassword() {
    return password;
  }

  public void setPassword(@Nullable String password) {
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
    UserRepresentation that = (UserRepresentation) o;
    return age == that.age &&
        Objects.equals(id, that.id) &&
        firstName.equals(that.firstName) &&
        lastName.equals(that.lastName) &&
        email.equals(that.email) &&
        password.equals(that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, age, email, password);
  }
}
