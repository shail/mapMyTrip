package org.goahead.server.api;

import com.google.common.base.Preconditions;

/**
 * Utility class for handling lat/lng along with validation
 */
public class LatLng {
  private final double lat;
  private final double lng;

  public LatLng(double lat, double lng) {
    this.lat = lat;
    this.lng = lng;
    validate();
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public void validate() {
    Preconditions.checkArgument(-90.0 < lat && lat < 90.0, "Lat value: {} must be between -90 and 90", lat);
    Preconditions.checkArgument(-180.0 < lng && lng < 180.0, "Lng value: {} must be between -180 and 180", lng);
  }
}
