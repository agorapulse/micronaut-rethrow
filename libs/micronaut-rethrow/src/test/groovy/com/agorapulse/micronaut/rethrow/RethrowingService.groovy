/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2019-2022 Agorapulse.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agorapulse.micronaut.rethrow

import groovy.transform.CompileStatic

import jakarta.inject.Singleton

@Singleton
@CompileStatic
class RethrowingService {

    public static final String NOT_IMPLEMENTED = 'not implemented'

    @Rethrow({ Throwable th -> new IllegalStateException('rethrowing', th) })
    void simpleRethrow() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED)
    }

    @Rethrow(as = IllegalStateException)
    void rethrowAs() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED)
    }

    @Rethrow(as = IllegalStateException, message = 'Something bad happened')
    void rethrowAsWithMessage() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED)
    }

    @Rethrow(RethrowingFunction)
    void rethrowWithFunctionalClass() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED)
    }

    @Rethrow(FailingClosure)
    void failingClosure() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED)
    }

    @Rethrow(FailingFunction)
    void failingFunction() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED)
    }

    @Rethrow(as = FailingException)
    void failingException() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED)
    }

    @Rethrow
    void defaultsToRuntimeException() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED)
    }

    @Rethrow(only = IllegalArgumentException)
    void rethrowOnly() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED)
    }

}
