package com.agorapulse.micronaut.rethrow;

import java.util.function.Function;

public class FailingFunction implements Function<Throwable, RuntimeException> {

    private FailingFunction() { }

    @Override
    public RuntimeException apply(Throwable throwable) {
        return null;
    }
}
