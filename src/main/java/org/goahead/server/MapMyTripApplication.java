package org.goahead.server;

import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.PolymorphicAuthDynamicFeature;
import io.dropwizard.auth.PolymorphicAuthValueFactoryProvider;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.goahead.server.auth.BasicAuthenticator;
import org.goahead.server.auth.BasicAuthorizer;
import org.goahead.server.auth.JwtAuthenticator;
import org.goahead.server.auth.JwtTokenGenerator;
import org.goahead.server.auth.TokenGenerator;
import org.goahead.server.auth.UserPrincipal;
import org.goahead.server.core.pojos.Trip;
import org.goahead.server.core.pojos.User;
import org.goahead.server.mapper.TripsMapper;
import org.goahead.server.mapper.UsersMapper;
import org.goahead.server.resources.LoginResource;
import org.goahead.server.resources.TripsResource;
import org.goahead.server.resources.UsersResource;
import org.goahead.server.service.TripsService;
import org.goahead.server.service.UsersService;
import org.jdbi.v3.core.Jdbi;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;

public class MapMyTripApplication extends Application<MapMyTripConfiguration> {

  public static void main(final String[] args) throws Exception {
    new MapMyTripApplication().run(args);
  }

  @Override
  public String getName() {
    return "mapMyTrip";
  }

  @Override
  public void initialize(final Bootstrap<MapMyTripConfiguration> bootstrap) {
    bootstrap.addBundle(new JdbiExceptionsBundle());
    // This needs to be added in order to sub in environment variables to the config file
    bootstrap.setConfigurationSourceProvider(
        new SubstitutingSourceProvider(
            bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
  }

  private void registerAuthFilters(
      Environment environment, UsersService usersService, String secret) {
    final AuthFilter<BasicCredentials, UserPrincipal> basicFilter =
        new BasicCredentialAuthFilter.Builder<UserPrincipal>()
            .setAuthenticator(new BasicAuthenticator(usersService))
            .setAuthorizer(new BasicAuthorizer())
            .buildAuthFilter();

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

    final PolymorphicAuthDynamicFeature feature =
        new PolymorphicAuthDynamicFeature<>(
            ImmutableMap.of(UserPrincipal.class, basicFilter, PrincipalImpl.class, jwtFilter));

    final AbstractBinder binder =
        new PolymorphicAuthValueFactoryProvider.Binder(
            ImmutableSet.of(UserPrincipal.class, PrincipalImpl.class));

    environment.jersey().register(feature);
    environment.jersey().register(binder);
    environment.jersey().register(RolesAllowedDynamicFeature.class);
  }

  @Override
  public void run(final MapMyTripConfiguration configuration, final Environment environment) {
    final JdbiFactory databaseFactory = new JdbiFactory();
    final Jdbi jdbi =
        databaseFactory.build(environment, configuration.getDataSourceFactory(), "mysql");
    final TripsService tripsDao = jdbi.onDemand(TripsService.class);
    jdbi.registerRowMapper(Trip.class, new TripsMapper());
    final UsersService userService = jdbi.onDemand(UsersService.class);
    final String secret = configuration.getCrypto().getSecret();
    final TokenGenerator tokenGenerator = new JwtTokenGenerator(secret);
    jdbi.registerRowMapper(User.class, new UsersMapper());
    environment.jersey().register(new TripsResource(tripsDao));
    environment.jersey().register(new UsersResource(userService));
    environment.jersey().register(new LoginResource(tokenGenerator));
    registerAuthFilters(environment, userService, secret);
  }
}
