package org.goahead.server.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
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
import org.goahead.server.service.TripsService;

@Path("/trips")
@Produces(MediaType.APPLICATION_JSON)
public class TripsResource {
  private final TripsService tripsService;

  public TripsResource(TripsService tripsService) {
    this.tripsService = Preconditions.checkNotNull(tripsService);
  }

  @GET
  @Timed
  public List<TripRepresentation> getTrips() {
    return tripsService.getTrips().stream()
        .map(trip -> new TripRepresentation(trip))
        .collect(Collectors.toList());
  }

  @GET
  @Timed
  @Path("{id}")
  public TripRepresentation getTrip(@PathParam("id") final int id) {
    Trip trip = tripsService.getTrip(id);
    if (trip == null) {
      throw new WebApplicationException("No trip exists with id: " + id, Status.NOT_FOUND);
    }
    return new TripRepresentation(trip);
  }

  @POST
  @Timed
  public Response createTrip(@NotNull @Valid final TripRepresentation trip) {
    return Response.ok(tripsService.createTrip(trip)).build();
  }

  @PUT
  @Timed
  public Response updateTrip(@NotNull @Valid final TripRepresentation trip) {
    return Response.ok(tripsService.updateTrip(trip)).build();
  }

  @DELETE
  @Timed
  @Path("{id}")
  public Response deleteTrip(@PathParam("id") final int id) {
    tripsService.deleteTrip(id);
    return Response.ok().build();
  }
}
