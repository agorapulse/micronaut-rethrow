package com.agorapulse.micronaut.rethrow

import groovy.transform.CompileDynamic
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
@CompileDynamic
class RethrowInterceptorSpec extends Specification {

    @Inject
    RethrowingService rethrowingService

    @Inject
    RethrowingJavaService rethrowingJavaService

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

}
