package com.agorapulse.micronaut.rethrow;

import java.util.function.Function;

public class RethrowingFunction implements Function<Throwable, RuntimeException> {

    @Override
    public RuntimeException apply(Throwable throwable) {
        return new IllegalStateException("rethrown from java", throwable);
    }
}
