package org.goahead.server.api;

import com.google.common.base.Preconditions;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

/** Simple Trip container object */
public class Trip {
  private Integer id;
  @NotEmpty private String name;
  @NotNull @Valid private LatLng latLng;

  public Trip() {}

  public Trip(Integer id, String name, LatLng latLng) {
    Preconditions.checkArgument(StringUtils.isNotEmpty(name));
    this.id = id;
    this.name = name;
    this.latLng = Preconditions.checkNotNull(latLng);
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

  // TODO: if id ends up being non-optional then update this method
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Trip trip = (Trip) o;
    return Objects.equals(id, trip.id) && name.equals(trip.name) && latLng.equals(trip.latLng);
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
