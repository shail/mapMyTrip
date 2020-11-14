package org.goahead.server.api;

import com.google.common.base.Preconditions;
import java.util.Objects;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/** Utility class for handling lat/lng along with validation */
public class LatLng {
  @DecimalMin(value = "-90.0", message = "Lat must be greater than -90.0")
  @DecimalMax(value = "90.0", message = "Lat must be less than 90.0")
  private double lat;

  @DecimalMin(value = "-180.0", message = "Lng must be greater than -180.0")
  @DecimalMax(value = "180.0", message = "Lng must be less than 180.0")
  private double lng;

  public LatLng() {}

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
    Preconditions.checkArgument(
        -90.0 < lat && lat < 90.0, "Lat value: {} must be between -90 and 90", lat);
    Preconditions.checkArgument(
        -180.0 < lng && lng < 180.0, "Lng value: {} must be between -180 and 180", lng);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LatLng latLng = (LatLng) o;
    return Double.compare(latLng.lat, lat) == 0 && Double.compare(latLng.lng, lng) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(lat, lng);
  }

  @Override
  public String toString() {
    return "LatLng{" + "lat=" + lat + ", lng=" + lng + '}';
  }
}
