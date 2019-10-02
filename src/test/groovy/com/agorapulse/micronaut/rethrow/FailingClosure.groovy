package com.agorapulse.micronaut.rethrow

import groovy.transform.CompileStatic

import java.util.function.Function

@CompileStatic
class FailingClosure extends Closure<RuntimeException> implements  Function<Throwable, RuntimeException> {

    private FailingClosure(Object owner, Object thisObject) {
        super(owner, thisObject)
    }

    @Override
    RuntimeException apply(Throwable throwable) {
        return null
    }

}
