package org.goahead.server.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import javax.ws.rs.core.Response;
import org.assertj.core.util.Lists;
import org.goahead.server.api.LatLng;
import org.goahead.server.api.TripRepresentation;
import org.goahead.server.core.pojos.Trip;
import org.goahead.server.service.TripsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TripsResourceTest {
  private static final TripsService tripsService = mock(TripsService.class);
  private static final ResourceExtension resources =
      ResourceExtension.builder().addResource(new TripsResource(tripsService)).build();
  private static final String TRIPS_ENDPOINT = "/trips";
  private final Trip trip = new Trip(1, "testTrip", new LatLng(1.0, 2.0));

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
}
