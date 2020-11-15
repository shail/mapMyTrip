package org.goahead.server.core.pojos;

import javax.validation.constraints.NotEmpty;
import org.goahead.server.api.UserRepresentation;

public class User {
  private int id;
  @NotEmpty
  private String firstName;
  @NotEmpty private String lastName;
  private int age;
  @NotEmpty private String email;
  @NotEmpty private String passwordHash;
  @NotEmpty private String salt;

  public User(final int id, final String firstName, final String lastName, final int age, final String email, final String passwordHash, final String salt) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.email = email;
    this.passwordHash = passwordHash;
    this.salt = salt;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
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

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }
}
