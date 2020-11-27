package org.goahead.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;

public class MapMyTripConfiguration extends Configuration {
  @Valid @NotNull
  // The database configuration will be read from a config file and this object handles that
  private DataSourceFactory database = new DataSourceFactory();

  @JsonProperty("database")
  public void setDataSourceFactory(DataSourceFactory factory) {
    this.database = factory;
  }

  @JsonProperty("database")
  public DataSourceFactory getDataSourceFactory() {
    return database;
  }

  @JsonProperty("crypto")
  public Crypto crypto;

  public static class Crypto {
    @JsonProperty("secret")
    public String secret;

    public String getSecret() {
      return secret;
    }
  }

  public DataSourceFactory getDatabase() {
    return database;
  }

  public void setDatabase(DataSourceFactory database) {
    this.database = database;
  }

  public Crypto getCrypto() {
    return crypto;
  }

  public void setCrypto(Crypto crypto) {
    this.crypto = crypto;
  }
}
