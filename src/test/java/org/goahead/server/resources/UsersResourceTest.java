package org.goahead.server.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.assertj.core.util.Lists;
import org.goahead.server.api.CreateUserRepresentation;
import org.goahead.server.api.UserRepresentation;
import org.goahead.server.core.pojos.User;
import org.goahead.server.service.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
public class UsersResourceTest {
  private static final UsersService usersService = mock(UsersService.class);
  private static final ResourceExtension resources =
      ResourceExtension.builder().addResource(new UsersResource(usersService)).build();
  private static final String USERS_ENDPOINT = "/users";
  private final User user = new User(1, "bob", "cobb", 10, "bob@cobb.com", "password", "salt");

  @Test
  public void testGetUsers() {
    when(usersService.getUsers()).thenReturn(Lists.newArrayList(user));
    UserRepresentation[] users =
        resources
            .target(USERS_ENDPOINT)
            .request()
            .get(Response.class)
            .readEntity(UserRepresentation[].class);

    assertThat(users.length).isEqualTo(1);
    assertThat(users[0]).isEqualTo(new UserRepresentation(user));
  }

  @Test
  public void testGetUser() {
    final int id = 1;
    when(usersService.getUser(eq(id))).thenReturn(user);
    UserRepresentation userResult =
        resources
            .target(USERS_ENDPOINT + "/" + id)
            .request()
            .get(Response.class)
            .readEntity(UserRepresentation.class);
    assertThat(userResult).isEqualTo(new UserRepresentation(user));
  }

  @Test
  public void testDeleteUser() {
    final int id = 1;
    when(usersService.deleteUser(eq(id))).thenReturn(true);
    Response deleteResponse =
        resources.target(USERS_ENDPOINT + "/" + id).request().delete(Response.class);
    assertThat(deleteResponse.getStatus()).isEqualTo(Status.OK.getStatusCode());
  }

  @Test
  public void testCreateUser() {
    when(usersService.createUser(new CreateUserRepresentation(user))).thenReturn(user);
    UserRepresentation createdUser =
        resources
            .target(USERS_ENDPOINT)
            .request()
            .post(
                Entity.entity(new CreateUserRepresentation(user), MediaType.APPLICATION_JSON_TYPE))
            .readEntity(UserRepresentation.class);
    assertThat(createdUser).isEqualTo(new UserRepresentation(user));
  }
}
