package org.goahead.server.core.pojos;

import com.google.common.base.Preconditions;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.goahead.server.api.LatLng;
import org.goahead.server.api.TripRepresentation;

/** Simple Trip container object */
public class Trip {
  private Integer id;
  @NotEmpty private String name;
  @NotNull @Valid private LatLng latLng;
  @NotNull private int userId;

  public Trip() {}

  public Trip(Integer id, String name, LatLng latLng, int userId) {
    Preconditions.checkArgument(StringUtils.isNotEmpty(name));
    this.id = id;
    this.name = name;
    this.latLng = Preconditions.checkNotNull(latLng);
    this.userId = userId;
  }

  public Trip(TripRepresentation tripRepresentation) {
    Preconditions.checkNotNull(tripRepresentation);
    this.id = tripRepresentation.getId();
    this.name = tripRepresentation.getName();
    this.latLng = tripRepresentation.getLatLng();
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

  public int getUserId() {
    return userId;
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

  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Trip trip = (Trip) o;
    return userId == trip.userId
        && id.equals(trip.id)
        && name.equals(trip.name)
        && latLng.equals(trip.latLng);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, latLng, userId);
  }

  @Override
  public String toString() {
    return "Trip{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", latLng="
        + latLng
        + ", userId="
        + userId
        + '}';
  }
}
