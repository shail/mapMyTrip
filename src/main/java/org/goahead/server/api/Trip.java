package org.goahead.server.api;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

/**
 * Simple Trip container object
 */
public class Trip {
  private Integer id;
  private String name;
  private LatLng latLng;

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
}
