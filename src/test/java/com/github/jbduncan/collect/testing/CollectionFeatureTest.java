package com.github.jbduncan.collect.testing;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CollectionFeatureTest {

  @Test
  public void valueOfInputNotNullOutputIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      CollectionFeature.valueOf("foo");
    });

    // The method is not expected to return due to exception thrown
  }
}
