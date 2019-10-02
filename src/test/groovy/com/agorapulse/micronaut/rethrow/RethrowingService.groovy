package com.agorapulse.micronaut.rethrow

import groovy.transform.CompileStatic

import javax.inject.Singleton

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
