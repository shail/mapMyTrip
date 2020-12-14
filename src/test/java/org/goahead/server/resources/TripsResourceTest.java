package org.goahead.server.resourceExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthValueFactoryProvider.Binder;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import java.util.UUID;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.assertj.core.util.Lists;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.goahead.server.api.LatLng;
import org.goahead.server.api.TripRepresentation;
import org.goahead.server.auth.JwtAuthenticator;
import org.goahead.server.auth.JwtTokenGenerator;
import org.goahead.server.auth.TokenGenerator;
import org.goahead.server.auth.UserPrincipal;
import org.goahead.server.core.pojos.Trip;
import org.goahead.server.resources.TripsResource;
import org.goahead.server.service.TripPointsService;
import org.goahead.server.service.TripsService;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TripsResourceTest {
  private static final TripsService tripsService = mock(TripsService.class);
  private static final TripPointsService tripsPointsService = mock(TripPointsService.class);
  private static final String tripsEndpoint = "/trips";
  private static final Trip trip = new Trip(1, "testTrip", new LatLng(1.0, 2.0), 1);
  private static final String secret = UUID.randomUUID().toString();
  private static ResourceExtension resourceExtension;

  static {
    final JwtConsumer consumer =
        new JwtConsumerBuilder()
            .setAllowedClockSkewInSeconds(30)
            .setRequireSubject()
            .setVerificationKey(new HmacKey(secret.getBytes()))
            .build();

    final AuthFilter<JwtContext, PrincipalImpl> jwtFilter =
        new JwtAuthFilter.Builder<PrincipalImpl>()
            .setJwtConsumer(consumer)
            .setAuthenticator(new JwtAuthenticator())
            .setPrefix("Bearer")
            .buildAuthFilter();
    resourceExtension =
        ResourceExtension.builder()
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addProvider(new AuthDynamicFeature(jwtFilter))
            .addProvider(new Binder<>(PrincipalImpl.class))
            .addResource(new TripsResource(tripsService, tripsPointsService))
            .build();
    when(tripsService.checkAccess(any(PrincipalImpl.class), anyInt())).thenReturn(true);
  }

  @Test
  public void testGetTrips() {
    when(tripsService.getTrips()).thenReturn(Lists.newArrayList(trip));
    String token = setupToken();
    TripRepresentation[] trips =
        resourceExtension
            .target(tripsEndpoint)
            .request()
            .header("Authorization", "Bearer " + token)
            .get(Response.class)
            .readEntity(TripRepresentation[].class);
    assertThat(1).isEqualTo(trips.length);
    assertThat(trip).isEqualTo(new Trip(trips[0]));
  }

  @Test
  public void testGetTrip() {
    final int id = 1;
    when(tripsService.getTrip(eq(id))).thenReturn(trip);
    when(tripsPointsService.getTripPointsForTrip(eq(trip.getId())))
        .thenReturn(Lists.newArrayList());
    String token = setupToken();
    TripRepresentation tripResult =
        resourceExtension
            .target(tripsEndpoint + "/" + id)
            .request()
            .header("Authorization", "Bearer " + token)
            .get(Response.class)
            .readEntity(TripRepresentation.class);
    assertThat(tripResult).isEqualTo(new TripRepresentation(trip));
  }

  @Test
  public void testDeleteTrip() {
    final int id = 1;
    String token = setupToken();
    when(tripsService.deleteTrip(eq(id))).thenReturn(true);
    Response deleteResponse =
        resourceExtension
            .target(tripsEndpoint + "/" + id)
            .request()
            .header("Authorization", "Bearer " + token)
            .delete(Response.class);
    assertThat(Status.OK.getStatusCode()).isEqualTo(deleteResponse.getStatus());
  }

  @Test
  public void testCreateTrip() {
    when(tripsService.createTrip(new TripRepresentation(trip))).thenReturn(trip);
    String token = setupToken();
    TripRepresentation createdTrip =
        resourceExtension
            .target(tripsEndpoint)
            .request()
            .header("Authorization", "Bearer " + token)
            .post(Entity.entity(new TripRepresentation(trip), MediaType.APPLICATION_JSON_TYPE))
            .readEntity(TripRepresentation.class);
    assertThat(new TripRepresentation(trip, null)).isEqualTo(createdTrip);
  }

  private String setupToken() {
    TokenGenerator tokenGenerator = new JwtTokenGenerator(secret);
    UserPrincipal userPrincipal = new UserPrincipal("shail");
    String token = tokenGenerator.getToken(userPrincipal);
    return token;
  }
}
