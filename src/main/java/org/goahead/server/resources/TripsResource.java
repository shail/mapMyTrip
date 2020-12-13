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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.goahead.server.api.TripRepresentation;
import org.goahead.server.core.pojos.Trip;
import org.goahead.server.core.pojos.TripPoint;
import org.goahead.server.service.TripPointsService;
import org.goahead.server.service.TripsService;

@Path("/trips")
@Produces(MediaType.APPLICATION_JSON)
public class TripsResource {
  private final TripsService tripsService;
  private final TripPointsService tripPointsService;

  public TripsResource(TripsService tripsService, TripPointsService tripPointsService) {
    this.tripsService = Preconditions.checkNotNull(tripsService);
    this.tripPointsService = Preconditions.checkNotNull(tripPointsService);
  }

  /** Validate that the user has access to read/write to a specific resource */
  private void checkAccess(final PrincipalImpl authUser, final int id) {
    if (!tripsService.checkAccess(authUser, id)) {
      throw new WebApplicationException(
          "Can't access trip for a user with a different id", Status.UNAUTHORIZED);
    }
  }

  @GET
  @Timed
  public List<TripRepresentation> getTrips(@Auth PrincipalImpl user) {
    return tripsService.getTrips().stream()
        .map(trip -> new TripRepresentation(trip))
        .collect(Collectors.toList());
  }

  @GET
  @Timed
  @Path("{id}")
  public TripRepresentation getTrip(@Auth PrincipalImpl user, @PathParam("id") final int id) {
    checkAccess(user, id);
    Trip trip = tripsService.getTrip(id);
    if (trip == null) {
      throw new WebApplicationException("No trip exists with id: " + id, Status.NOT_FOUND);
    }
    List<TripPoint> tripPoints = tripPointsService.getTripPointsForTrip(trip.getId());
    return new TripRepresentation(trip, tripPoints);
  }

  @POST
  @Timed
  public Response createTrip(
      @Auth PrincipalImpl user, @NotNull @Valid final TripRepresentation trip) {
    return Response.ok(tripsService.createTrip(trip)).build();
  }

  @PUT
  @Timed
  public Response updateTrip(
      @Auth PrincipalImpl user, @NotNull @Valid final TripRepresentation trip) {
    checkAccess(user, trip.getId());
    return Response.ok(tripsService.updateTrip(trip)).build();
  }

  @DELETE
  @Timed
  @Path("{id}")
  public Response deleteTrip(@Auth PrincipalImpl user, @PathParam("id") final int id) {
    checkAccess(user, id);
    tripsService.deleteTrip(id);
    return Response.ok().build();
  }
}
