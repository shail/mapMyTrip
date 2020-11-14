package org.goahead.server.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
import java.util.List;
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
import org.goahead.server.api.Trip;
import org.goahead.server.dao.TripsDao;

@Path("/trips")
@Produces(MediaType.APPLICATION_JSON)
public class TripsResource {
  private final TripsDao tripsDao;

  public TripsResource(TripsDao tripsDao) {
    this.tripsDao = Preconditions.checkNotNull(tripsDao);
  }

  @GET
  @Timed
  public List<Trip> getTrips() {
    return tripsDao.getTrips();
  }

  @GET
  @Timed
  @Path("{id}")
  public Trip getTrips(@PathParam("id") final int id) {
    Trip trip = tripsDao.getTrip(id);
    if (trip == null) {
      throw new WebApplicationException("No trip exists with id: " + id, Status.NOT_FOUND);
    }
    return trip;
  }

  @POST
  @Timed
  public Response createTrip(@NotNull @Valid final Trip trip) {
    return Response.ok(String.valueOf(tripsDao.createTrip(trip))).build();
  }

  @PUT
  @Timed
  public Response updateTrip(@NotNull @Valid final Trip trip) {
    tripsDao.updateTrip(trip);
    return Response.ok().build();
  }

  @DELETE
  @Timed
  @Path("{id}")
  public Response deleteTrip(@PathParam("id") final int id) {
    if (tripsDao.deleteTrip(id)) {
      return Response.ok().build();
    }
    throw new WebApplicationException("No trip to delete with id: " + id, Status.NOT_FOUND);
  }
}
