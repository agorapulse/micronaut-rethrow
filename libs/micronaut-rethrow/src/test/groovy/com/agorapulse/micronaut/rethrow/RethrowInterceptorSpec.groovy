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

import groovy.transform.CompileDynamic
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

import jakarta.inject.Inject

@MicronautTest
@CompileDynamic
class RethrowInterceptorSpec extends Specification {

    @Inject
    RethrowingService rethrowingService

    @Inject
    RethrowingJavaService rethrowingJavaService

    @Inject
    RetryableService retryableService

    void 'simple test'() {
        when:
            rethrowingService.simpleRethrow()
        then:
            IllegalStateException ise = thrown(IllegalStateException)

            ise.message == 'rethrowing'
            ise.cause instanceof UnsupportedOperationException
    }

    void 'as test'() {
        when:
            rethrowingService.rethrowAs()
        then:
            IllegalStateException ise = thrown(IllegalStateException)

            ise.cause instanceof UnsupportedOperationException
    }

    void 'as test with message'() {
        when:
            rethrowingService.rethrowAsWithMessage()
        then:
            IllegalStateException ise = thrown(IllegalStateException)

            ise.message == 'Something bad happened'
            ise.cause instanceof UnsupportedOperationException
    }

    void 'rethrow from java on class level'() {
        when:
            rethrowingJavaService.rethrowSomethingDeclaredOnClass()
        then:
            IllegalStateException ise = thrown(IllegalStateException)

            ise.cause instanceof UnsupportedOperationException
    }

    void 'java functional class'() {
        when:
            rethrowingService.rethrowWithFunctionalClass()
        then:
            IllegalStateException ise = thrown(IllegalStateException)

            ise.cause instanceof UnsupportedOperationException
    }

    void 'failing closure'() {
        when:
            rethrowingService.failingClosure()
        then:
            thrown(IllegalArgumentException)
    }

    void 'failing function'() {
        when:
            rethrowingService.failingFunction()
        then:
            thrown(IllegalArgumentException)
    }

    void 'failing exception'() {
        when:
            rethrowingService.failingException()
        then:
            thrown(IllegalArgumentException)
    }

    void 'defaults to RuntimeException'() {
        when:
            rethrowingService.defaultsToRuntimeException()
        then:
            thrown(RuntimeException)
    }

    void 'filter only certain errors'() {
        when:
            rethrowingService.rethrowOnly()
        then:
            thrown(UnsupportedOperationException)
    }

    void 'rethrow and retryable'() {
        when:
            retryableService.retryOnException()

        then:
            thrown(IllegalStateException)

            retryableService.counter == 4
    }

}
