package com.github.jbduncan.collect.testing;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.Set;

public interface Feature<T> {
  Set<Feature<? super T>> impliedFeatures();

  static Set<Feature<?>> allFeatures(Feature<?>... features) {
    Set<Feature<?>> expandedFeatures = Helpers.copyToMutableInsertionOrderSet(features);
    Queue<Feature<?>> queue = new ArrayDeque<>(expandedFeatures);
    while (!queue.isEmpty()) {
      Feature<?> next = queue.remove();
      for (Feature<?> implied : next.impliedFeatures()) {
        if (expandedFeatures.add(implied)) {
          queue.add(implied);
        }
      }
    }
    return Collections.unmodifiableSet(expandedFeatures);
  }
}