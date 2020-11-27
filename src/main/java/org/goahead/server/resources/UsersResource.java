package org.goahead.server.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import io.dropwizard.auth.PrincipalImpl;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.goahead.server.api.CreateUserRepresentation;
import org.goahead.server.api.UserRepresentation;
import org.goahead.server.core.pojos.User;
import org.goahead.server.service.UsersService;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {
  private final UsersService usersService;

  public UsersResource(UsersService usersService) {
    this.usersService = Preconditions.checkNotNull(usersService);
  }

  @GET
  @Timed
  public List<UserRepresentation> getUsers(@Auth PrincipalImpl authUser) {
    return usersService.getUsers().stream()
        .map(user -> new UserRepresentation(user))
        .collect(Collectors.toList());
  }

  @GET
  @Timed
  @Path("{id}")
  public Response getUser(@Auth PrincipalImpl authUser, @PathParam("id") final int id) {
    User user = usersService.getUser(id);
    if (user == null) {
      throw new WebApplicationException("No user exists with id: " + id);
    }
    return Response.ok(usersService.getUser(id)).build();
  }

  @POST
  @Timed
  public Response createUser(
      @NotNull @Valid final CreateUserRepresentation createUserRepresentation) {
    return Response.ok(new UserRepresentation(usersService.createUser(createUserRepresentation)))
        .build();
  }

  @DELETE
  @Timed
  @Path("{id}")
  public Response deleteUser(@Auth PrincipalImpl user, @PathParam("id") final int id) {
    usersService.deleteUser(id);
    return Response.ok().build();
  }
}
