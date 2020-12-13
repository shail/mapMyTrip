package org.goahead.server.core.pojos;

import com.google.common.base.Preconditions;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.goahead.server.api.LatLng;

/**
 * This class is a simple representation of a point that is associated with a trip.
 */
public class TripPoint {
  private Integer id;
  @NotNull private int tripId;
  @NotNull @Valid private LatLng latLng;
  @NotNull private long timestampMs;

  public TripPoint() {}

  public TripPoint(Integer id, int tripId, LatLng latLng, long timestampMs) {
    this.id = id;
    this.tripId = tripId;
    this.latLng = Preconditions.checkNotNull(latLng);
    this.timestampMs = timestampMs;
  }

  public long getTimestampMs() {
    return timestampMs;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public int getTripId() {
    return tripId;
  }

  public void setTripId(int tripId) {
    this.tripId = tripId;
  }

  public void setTimestampMs(long timestampMs) {
    this.timestampMs = timestampMs;
  }

  public LatLng getLatLng() {
    return latLng;
  }

  public void setLatLng(LatLng latLng) {
    this.latLng = latLng;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TripPoint tripPoint = (TripPoint) o;
    return tripId == tripPoint.tripId &&
        timestampMs == tripPoint.timestampMs &&
        Objects.equals(id, tripPoint.id) &&
        latLng.equals(tripPoint.latLng);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, tripId, latLng, timestampMs);
  }

  @Override
  public String toString() {
    return "TripPoint{" +
        "id=" + id +
        ", tripId=" + tripId +
        ", latLng=" + latLng +
        ", timestampMs=" + timestampMs +
        '}';
  }
}
