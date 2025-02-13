/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.mllib.optimization.tfocs

import scala.language.implicitConversions

/**
 * Helpers to perform validation when zipping two iterators, available using an implicit conversion.
 */
private[tfocs] class CheckedIteratorFunctions[A](self: Iterator[A]) {

  /** Zip two iterators, validating that the iterators are the same size. */
  def checkedZip[B](that: Iterator[B]): Iterator[(A, B)] =
    new Iterator[(A, B)] {
      def hasNext: Boolean = (self.hasNext, that.hasNext) match {
        case (true, true) => true
        case (false, false) => false
        case _ => throw new IllegalArgumentException("Can only checkedZip Iterators with the " +
          "same number of elements")
      }
      def next(): (A, B) = (self.next(), that.next())
    }
}

private[tfocs] object CheckedIteratorFunctions {

  implicit def iteratorToCheckedIterator[T](iterator: Iterator[T]): CheckedIteratorFunctions[T] =
    new CheckedIteratorFunctions(iterator)
}
