package com.agorapulse.micronaut.rethrow;

import java.util.function.Function;

public class RethrowAsRuntimeException implements Function<Throwable, RuntimeException> {

    @Override
    public RuntimeException apply(Throwable throwable) {
        return new RuntimeException(throwable);
    }

}
