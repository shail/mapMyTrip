package org.goahead.server.api;

import com.google.common.base.Preconditions;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.goahead.server.core.pojos.Trip;

/** Simple Trip request/response object */
public class TripRepresentation {
  @Nullable
  private Integer id;
  @NotEmpty private String name;
  @NotNull @Valid private LatLng latLng;

  public TripRepresentation() {}

  public TripRepresentation(Integer id, String name, LatLng latLng) {
    Preconditions.checkArgument(StringUtils.isNotEmpty(name));
    this.id = id;
    this.name = name;
    this.latLng = Preconditions.checkNotNull(latLng);
  }

  public TripRepresentation(Trip trip) {
    Preconditions.checkNotNull(trip);
    this.id = trip.getId();
    this.name = trip.getName();
    this.latLng = trip.getLatLng();
  }

  public String getName() {
    return name;
  }

  public Integer getId() {
    return id;
  }

  public LatLng getLatLng() {
    return latLng;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
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
    TripRepresentation tripRepresentation = (TripRepresentation) o;
    return Objects.equals(id, tripRepresentation.id) && name.equals(tripRepresentation.name) && latLng.equals(
        tripRepresentation.latLng);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, latLng);
  }

  @Override
  public String toString() {
    return "Trip{" + "id=" + id + ", name='" + name + '\'' + ", latLng=" + latLng + '}';
  }
}
