package com.github.jbduncan.collect.testing;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class ListFeatureTests {
  @Test
  void supportsSetHasExpectedImpliedFeatures() {
    assertThat(ListFeature.SUPPORTS_SET.impliedFeatures()).isEmpty();
    assertThat(Feature.allFeatures(ListFeature.SUPPORTS_SET))
        .containsExactly(ListFeature.SUPPORTS_SET);
  }

  @Test
  void supportsAddWithIndexHasExpectedImpliedFeatures() {
    assertThat(ListFeature.SUPPORTS_ADD_WITH_INDEX.impliedFeatures())
        .containsExactly(CollectionFeature.SUPPORTS_ADD);
    assertThat(Feature.allFeatures(ListFeature.SUPPORTS_ADD_WITH_INDEX))
        .containsExactly(ListFeature.SUPPORTS_ADD_WITH_INDEX, CollectionFeature.SUPPORTS_ADD);
  }

  @Test
  void supportsRemoveWithIndexHasExpectedImpliedFeatures() {
    assertThat(ListFeature.SUPPORTS_REMOVE_WITH_INDEX.impliedFeatures())
        .containsExactly(CollectionFeature.SUPPORTS_REMOVE);
    assertThat(Feature.allFeatures(ListFeature.SUPPORTS_REMOVE_WITH_INDEX))
        .containsExactly(ListFeature.SUPPORTS_REMOVE_WITH_INDEX, CollectionFeature.SUPPORTS_REMOVE);
  }

  @Test
  void generalPurposeHasExpectedImpliedFeatures() {
    assertThat(ListFeature.GENERAL_PURPOSE.impliedFeatures())
        .containsExactly(
            CollectionFeature.GENERAL_PURPOSE,
            ListFeature.SUPPORTS_SET,
            ListFeature.SUPPORTS_ADD_WITH_INDEX,
            ListFeature.SUPPORTS_REMOVE_WITH_INDEX);
    assertThat(Feature.allFeatures(ListFeature.GENERAL_PURPOSE))
        .containsExactly(
            ListFeature.GENERAL_PURPOSE,
            CollectionFeature.GENERAL_PURPOSE,
            CollectionFeature.SUPPORTS_ADD,
            CollectionFeature.SUPPORTS_REMOVE,
            CollectionFeature.SUPPORTS_ITERATOR_REMOVE,
            ListFeature.SUPPORTS_SET,
            ListFeature.SUPPORTS_ADD_WITH_INDEX,
            ListFeature.SUPPORTS_REMOVE_WITH_INDEX);
  }

  @Test
  void removeOperationsHasExpectedImpliedFeatures() {
    assertThat(ListFeature.REMOVE_OPERATIONS.impliedFeatures())
        .containsExactly(
            CollectionFeature.REMOVE_OPERATIONS, ListFeature.SUPPORTS_REMOVE_WITH_INDEX);
    assertThat(Feature.allFeatures(ListFeature.REMOVE_OPERATIONS))
        .containsExactly(
            ListFeature.REMOVE_OPERATIONS,
            CollectionFeature.REMOVE_OPERATIONS,
            ListFeature.SUPPORTS_REMOVE_WITH_INDEX,
            CollectionFeature.SUPPORTS_REMOVE,
            CollectionFeature.SUPPORTS_ITERATOR_REMOVE);
  }
}