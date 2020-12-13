package org.goahead.server.api;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.goahead.server.core.pojos.Trip;
import org.goahead.server.core.pojos.TripPoint;

/** Simple Trip request/response object */
public class TripRepresentation {
  @Nullable private Integer id;
  @NotEmpty private String name;
  @NotNull @Valid private LatLng latLng;
  @Nullable private Integer userId;
  @Nullable private List<TripPoint> points;

  public TripRepresentation() {}

  public TripRepresentation(Integer id, String name, LatLng latLng, Integer userId, List<TripPoint> points) {
    Preconditions.checkArgument(StringUtils.isNotEmpty(name));
    this.id = id;
    this.name = name;
    this.latLng = Preconditions.checkNotNull(latLng);
    this.userId = userId;
    this.points = points;
  }

  public TripRepresentation(Trip trip) {
    this(Preconditions.checkNotNull(trip).getId(), trip.getName(), trip.getLatLng(), trip.getUserId(), Lists.newArrayList());
  }

  public TripRepresentation(Trip trip, List<TripPoint> points) {
    this(Preconditions.checkNotNull(trip).getId(), trip.getName(), trip.getLatLng(), trip.getUserId(), points);
  }

  public List<TripPoint> getPoints() {
    return points;
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

  @Nullable
  public Integer getUserId() {
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

  public void setUserId(@Nullable Integer userId) {
    this.userId = userId;
  }

  public void setPoints(List<TripPoint> points) {
    this.points = points;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TripRepresentation that = (TripRepresentation) o;
    return Objects.equals(id, that.id) &&
        name.equals(that.name) &&
        latLng.equals(that.latLng) &&
        userId.equals(that.userId) &&
        Objects.equals(points, that.points);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, latLng, userId, points);
  }

  @Override
  public String toString() {
    return "TripRepresentation{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", latLng=" + latLng +
        ", userId=" + userId +
        ", points=" + points +
        '}';
  }
}
