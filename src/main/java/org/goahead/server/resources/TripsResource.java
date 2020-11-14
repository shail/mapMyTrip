package org.goahead.server.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

  @POST
  @Timed
  public Response createTrip(@NotNull @Valid final Trip trip) {
    return Response.ok(String.valueOf(tripsDao.createTrip(trip))).build();
  }
}
