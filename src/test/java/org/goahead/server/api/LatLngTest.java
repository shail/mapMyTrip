package org.goahead.server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LatLngTest {
  @Test
  public void testValidLatLng() {
    final double expectedLat = 1.0;
    final double expectedLng = 1.0;
    final LatLng validLatLng = new LatLng(expectedLat, expectedLng);
    assertEquals(expectedLat, validLatLng.getLat());
    assertEquals(expectedLng, validLatLng.getLng());
  }

  @Test
  public void testInvalidLatLng() {
    final double expectedLat = -1000.0;
    final double expectedLng = 1.0;
    assertThrows(IllegalArgumentException.class, () -> {
      new LatLng(expectedLat, expectedLng);
    });
  }
}
