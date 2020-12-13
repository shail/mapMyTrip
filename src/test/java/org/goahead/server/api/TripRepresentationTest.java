package org.goahead.server.api;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.goahead.server.core.pojos.Trip;
import org.junit.Test;

public class TripRepresentationTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private static final String TRIP_FIXTURE_PATH = "fixtures/api/trip.json";

  @Test
  public void serializesToJson() throws Exception {
    // create a trip object
    final Trip trip = new Trip(1, "Boston Trip", new LatLng(1.0, 2.0), 1);
    // read the JSON from a file and compare that serializing the Trip object results in what is
    // expected
    final String expected =
        MAPPER.writeValueAsString(MAPPER.readValue(fixture(TRIP_FIXTURE_PATH), Trip.class));
    assertThat(MAPPER.writeValueAsString(trip)).isEqualTo(expected);
  }

  @Test
  public void deserializesFromJson() throws Exception {
    // create a trip object
    final Trip trip = new Trip(1, "Boston Trip", new LatLng(1.0, 2.0), 1);
    // deserialize object from JSON and make sure the fields are what you expected
    final Trip expectedTrip = MAPPER.readValue(fixture(TRIP_FIXTURE_PATH), Trip.class);
    assertThat(expectedTrip.getId()).isEqualTo(trip.getId());
    assertThat(expectedTrip.getName()).isEqualTo(trip.getName());
    assertThat(expectedTrip.getLatLng()).isEqualTo(trip.getLatLng());
  }
}
