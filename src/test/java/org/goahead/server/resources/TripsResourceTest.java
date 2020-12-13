package org.goahead.server.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableSet;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.PolymorphicAuthValueFactoryProvider;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.assertj.core.util.Lists;
import org.goahead.server.api.LatLng;
import org.goahead.server.api.TripRepresentation;
import org.goahead.server.auth.UserPrincipal;
import org.goahead.server.core.pojos.Trip;
import org.goahead.server.service.TripPointsService;
import org.goahead.server.service.TripsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TripsResourceTest {
  private static final TripsService tripsService = mock(TripsService.class);
  private static final TripPointsService tripsPointsService = mock(TripPointsService.class);
  // TODO: fix this auth test
  private static final ResourceExtension resources =
      ResourceExtension.builder()
      .addProvider(new PolymorphicAuthValueFactoryProvider.Binder(
      ImmutableSet.of(UserPrincipal.class, PrincipalImpl.class)))
          .addResource(new TripsResource(tripsService, tripsPointsService)).build();
  private static final String TRIPS_ENDPOINT = "/trips";
  private final Trip trip = new Trip(1, "testTrip", new LatLng(1.0, 2.0), 1);

  @Test
  public void testGetTrips() {
    when(tripsService.getTrips()).thenReturn(Lists.newArrayList(trip));
    TripRepresentation[] trips =
        resources
            .target(TRIPS_ENDPOINT)
            .request()
            .get(Response.class)
            .readEntity(TripRepresentation[].class);
    assertThat(trips.length).isEqualTo(1);
    assertThat(new Trip(trips[0])).isEqualTo(trip);
  }

  @Test
  public void testGetTrip() {
    final int id = 1;
    when(tripsService.getTrip(eq(id))).thenReturn(trip);
    when(tripsPointsService.getTripPointsForTrip(eq(trip.getId()))).thenReturn(Lists.newArrayList());
    TripRepresentation tripResult =
        resources
            .target(TRIPS_ENDPOINT + "/" + id)
            .request()
            .get(Response.class)
            .readEntity(TripRepresentation.class);
    assertThat(tripResult).isEqualTo(new TripRepresentation(trip));
  }

  @Test
  public void testDeleteTrip() {
    final int id = 1;
    when(tripsService.deleteTrip(eq(id))).thenReturn(true);
    Response deleteResponse =
        resources.target(TRIPS_ENDPOINT + "/" + id).request().delete(Response.class);
    assertThat(deleteResponse.getStatus()).isEqualTo(Status.OK.getStatusCode());
  }

  @Test
  public void testCreateTrip() {
    when(tripsService.createTrip(new TripRepresentation(trip))).thenReturn(trip);
    TripRepresentation createdTrip =
        resources
            .target(TRIPS_ENDPOINT)
            .request()
            .post(Entity.entity(new TripRepresentation(trip), MediaType.APPLICATION_JSON_TYPE))
            .readEntity(TripRepresentation.class);
    assertThat(createdTrip).isEqualTo(new TripRepresentation(trip));
  }
}
