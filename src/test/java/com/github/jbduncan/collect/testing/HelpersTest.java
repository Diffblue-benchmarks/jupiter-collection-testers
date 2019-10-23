package com.github.jbduncan.collect.testing;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

public class HelpersTest {



  @Test
  public void copyToMutableInsertionOrderSetInput0OutputNotNull() {

    // Arrange
    final Object[] elements = {};

    // Act
    final Set actual = Helpers.copyToMutableInsertionOrderSet(elements);

    // Assert result
    final LinkedHashSet linkedHashSet = new LinkedHashSet();
    assertThat(actual).isEqualTo(linkedHashSet);
  }


  @Test
  public void insertInputNullZeroZeroOutputNullPointerException() {

    // Arrange
    final Iterable iterable = null;
    final int index = 0;
    final Object toInsert = 0;

    // Act
    assertThrows(NullPointerException.class, () -> {
      Helpers.insert(iterable, index, toInsert);
    });

    // The method is not expected to return due to exception thrown
  }


  @Test
  public void newIterableInputNullNotNullFalseOutputNull() {

    // Arrange
    final CollectionSize collectionSize = CollectionSize.SUPPORTS_ZERO;

    // Act and Assert result
    assertThat(Helpers.newIterable(null, collectionSize, false)).isEqualTo(null);
  }


  @Test
  public void stringifyInputNegativeOutputNotNull() {

    // Act and Assert result
    assertThat(Helpers.stringify(-524_288)).isEqualTo("\"-524288\"");
  }


  @Test
  public void stringifyInputNullOutputNotNull() {

    // Act and Assert result
    assertThat(Helpers.stringify(null)).isEqualTo(null);
  }
}
