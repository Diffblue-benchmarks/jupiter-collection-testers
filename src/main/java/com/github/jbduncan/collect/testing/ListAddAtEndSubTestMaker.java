/*
 * Copyright 2018-2019 the Jupiter Collection Testers authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jbduncan.collect.testing;

import static com.github.jbduncan.collect.testing.Helpers.append;
import static com.github.jbduncan.collect.testing.Helpers.newCollectionOfSize;
import static com.github.jbduncan.collect.testing.Helpers.newCollectionWithNullInMiddleOfSize;
import static com.github.jbduncan.collect.testing.Helpers.stringify;
import static com.github.jbduncan.collect.testing.Helpers.stringifyElements;
import static com.github.jbduncan.collect.testing.ListContractHelpers.newListToTest;
import static com.github.jbduncan.collect.testing.ListContractHelpers.newListToTestWithNullElementInMiddle;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.ThrowingConsumer;

final class ListAddAtEndSubTestMaker<E> {
  private static final String INDEX_TO_ADD_AT = "size()";

  private final TestListGenerator<E> generator;
  private final SampleElements<E> samples;
  private final E newElement;
  private final E existingElement;
  private final Set<CollectionSize> allSupportedCollectionSizesExceptZero;

  private ListAddAtEndSubTestMaker(Builder<E> builder) {
    this.generator = requireNonNull(builder.testListGenerator, "testListGenerator");
    this.samples = requireNonNull(builder.sampleElements, "samples");
    this.newElement = requireNonNull(builder.newElement, "newElement");
    this.existingElement = requireNonNull(builder.existingElement, "existingElement");
    this.allSupportedCollectionSizesExceptZero =
        requireNonNull(
            builder.allSupportedCollectionSizesExceptZero, "allSupportedCollectionSizesExceptZero");
  }

  static <E> Builder<E> builder() {
    return new Builder<>();
  }

  static final class Builder<E> {
    private Builder() {}

    private TestListGenerator<E> testListGenerator;
    private SampleElements<E> sampleElements;
    private E newElement;
    private E existingElement;
    private Set<CollectionSize> allSupportedCollectionSizesExceptZero;

    Builder<E> testListGenerator(TestListGenerator<E> testListGenerator) {
      this.testListGenerator = testListGenerator;
      return this;
    }

    Builder<E> sampleElements(SampleElements<E> sampleElements) {
      this.sampleElements = sampleElements;
      return this;
    }

    Builder<E> newElement(E newElement) {
      this.newElement = newElement;
      return this;
    }

    Builder<E> existingElement(E existingElement) {
      this.existingElement = existingElement;
      return this;
    }

    Builder<E> allSupportedCollectionSizesExceptZero(
        Set<CollectionSize> allSupportedCollectionSizesExceptZero) {
      this.allSupportedCollectionSizesExceptZero = allSupportedCollectionSizesExceptZero;
      return this;
    }

    ListAddAtEndSubTestMaker<E> build() {
      return new ListAddAtEndSubTestMaker<>(this);
    }
  }

  List<DynamicTest> supportsAddWithIndexSubTests() {
    List<DynamicTest> subTests = new ArrayList<>();
    appendSupportsAddAtEndWithNewElement(subTests);
    appendSupportsAddAtEndWithExistingElement(subTests);
    return subTests;
  }

  private void appendSupportsAddAtEndWithNewElement(List<DynamicTest> subTests) {
    appendSupportsAddAtEndImpl(subTests, newElement);
  }

  private void appendSupportsAddAtEndWithExistingElement(List<DynamicTest> subTests) {
    appendSupportsAddAtEndImpl(subTests, existingElement);
  }

  List<DynamicTest> supportsAddWithIndexForNullsSubTests() {
    List<DynamicTest> subTests = new ArrayList<>();
    appendSupportsAddAtStartWithNewNull(subTests);
    appendSupportsAddAtStartWithExistingNull(subTests);
    return subTests;
  }

  private void appendSupportsAddAtStartWithNewNull(List<DynamicTest> subTests) {
    appendSupportsAddAtEndImpl(subTests, null);
  }

  private void appendSupportsAddAtStartWithExistingNull(List<DynamicTest> subTests) {
    ThrowingConsumer<CollectionSize> supportsAddAtEndWithExistingNullElement =
        collectionSize -> {
          List<E> list = newListToTestWithNullElementInMiddle(generator, collectionSize);

          list.add(list.size(), null);
          List<E> expected =
              append(newCollectionWithNullInMiddleOfSize(collectionSize, samples), null);
          assertIterableEquals(
              expected, list, ListContractConstants.NOT_TRUE_THAT_LIST_WAS_APPENDED_WITH_NULL);
        };

    DynamicTest.stream(
            allSupportedCollectionSizesExceptZero.iterator(),
            collectionSize ->
                String.format(
                    ListContractConstants.FORMAT_SUPPORTS_LIST_ADD_WITH_INDEX,
                    "size()",
                    "null",
                    stringifyElements(
                        newCollectionWithNullInMiddleOfSize(collectionSize, samples))),
            supportsAddAtEndWithExistingNullElement)
        .forEachOrdered(subTests::add);
  }

  List<DynamicTest> doesNotSupportAddWithIndexSubTests() {
    List<DynamicTest> subTests = new ArrayList<>();
    appendDoesNotSupportAddAtEndWithNewElement(subTests);
    appendDoesNotSupportAddAtEndWithExistingElement(subTests);
    return subTests;
  }

  private void appendDoesNotSupportAddAtEndWithNewElement(List<DynamicTest> subTests) {
    appendDoesNotSupportAddAtEndImpl(subTests, newElement);
  }

  private void appendDoesNotSupportAddAtEndWithExistingElement(List<DynamicTest> subTests) {
    appendDoesNotSupportAddAtEndImpl(subTests, existingElement);
  }

  List<DynamicTest> doesNotSupportAddWithIndexForNullsSubTests() {
    List<DynamicTest> subTests = new ArrayList<>();
    appendDoesNotSupportAddAtEndWithNewNull(subTests);
    appendDoesNotSupportAddAtEndWithExistingNull(subTests);
    return subTests;
  }

  private void appendDoesNotSupportAddAtEndWithNewNull(List<DynamicTest> subTests) {
    appendDoesNotSupportAddAtEndImpl(subTests, null);
  }

  private void appendDoesNotSupportAddAtEndWithExistingNull(List<DynamicTest> subTests) {
    ThrowingConsumer<CollectionSize> doesNotSupportAddAtEndWithExistingNullElement =
        collectionSize -> {
          List<E> list = newListToTestWithNullElementInMiddle(generator, collectionSize);

          assertThrows(
              UnsupportedOperationException.class,
              () -> list.add(list.size(), null),
              () ->
                  String.format(
                      ListContractConstants
                          .FORMAT_NOT_TRUE_THAT_LIST_ADD_THREW_UNSUPPORTED_OPERATION_EXCEPTION,
                      "null"));
          assertIterableEquals(
              newCollectionWithNullInMiddleOfSize(collectionSize, samples),
              list,
              ListContractConstants.NOT_TRUE_THAT_LIST_REMAINED_UNCHANGED);
        };

    DynamicTest.stream(
            allSupportedCollectionSizesExceptZero.iterator(),
            collectionSize ->
                String.format(
                    ListContractConstants.FORMAT_DOESNT_SUPPORT_LIST_ADD_WITH_INDEX,
                    INDEX_TO_ADD_AT,
                    "null",
                    stringifyElements(
                        newCollectionWithNullInMiddleOfSize(collectionSize, samples))),
            doesNotSupportAddAtEndWithExistingNullElement)
        .forEachOrdered(subTests::add);
  }

  private void appendSupportsAddAtEndImpl(List<DynamicTest> subTests, E elementToAdd) {
    ThrowingConsumer<CollectionSize> supportsAddAtEnd =
        collectionSize -> {
          List<E> list = newListToTest(generator, collectionSize);

          list.add(list.size(), elementToAdd);
          List<E> expected = append(newCollectionOfSize(collectionSize, samples), elementToAdd);
          assertIterableEquals(
              expected,
              list,
              () ->
                  String.format(
                      ListContractConstants.FORMAT_NOT_TRUE_THAT_LIST_WAS_APPENDED,
                      stringify(elementToAdd)));
        };

    // [].add(size(), E) is already indirectly tested by ListAddAtStartSubTestMaker, because it
    // tests [].add(0, E), and List.size() == 0 for empty lists, so we skip
    // CollectionSize.SUPPORTS_ZERO.
    DynamicTest.stream(
            allSupportedCollectionSizesExceptZero.iterator(),
            collectionSize ->
                String.format(
                    ListContractConstants.FORMAT_SUPPORTS_LIST_ADD_WITH_INDEX,
                    INDEX_TO_ADD_AT,
                    stringify(elementToAdd),
                    stringifyElements(newCollectionOfSize(collectionSize, samples))),
            supportsAddAtEnd)
        .forEachOrdered(subTests::add);
  }

  private void appendDoesNotSupportAddAtEndImpl(List<DynamicTest> subTests, E elementToAdd) {
    ThrowingConsumer<CollectionSize> doesNotSupportAddAtEnd =
        collectionSize -> {
          List<E> list = newListToTest(generator, collectionSize);

          assertThrows(
              UnsupportedOperationException.class,
              () -> list.add(list.size(), elementToAdd),
              () ->
                  String.format(
                      ListContractConstants
                          .FORMAT_NOT_TRUE_THAT_LIST_ADD_THREW_UNSUPPORTED_OPERATION_EXCEPTION,
                      stringify(elementToAdd)));
          assertIterableEquals(
              newCollectionOfSize(collectionSize, samples),
              list,
              ListContractConstants.NOT_TRUE_THAT_LIST_REMAINED_UNCHANGED);
        };

    // [].add(size(), E) is already indirectly tested by ListAddAtStartSubTestMaker, because it
    // tests [].add(0, E), and List.size() == 0 for empty lists, so we skip
    // CollectionSize.SUPPORTS_ZERO.
    DynamicTest.stream(
            allSupportedCollectionSizesExceptZero.iterator(),
            collectionSize ->
                String.format(
                    ListContractConstants.FORMAT_DOESNT_SUPPORT_LIST_ADD_WITH_INDEX,
                    INDEX_TO_ADD_AT,
                    stringify(elementToAdd),
                    stringifyElements(newCollectionOfSize(collectionSize, samples))),
            doesNotSupportAddAtEnd)
        .forEachOrdered(subTests::add);
  }
}
