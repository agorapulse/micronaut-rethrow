package com.agorapulse.micronaut.rethrow;

import javax.inject.Singleton;

@Singleton
@Rethrow(as = IllegalStateException.class)
public class RethrowingJavaService {

    void rethrowSomethingDeclaredOnClass() {
        throw new UnsupportedOperationException("not implemented");
    }

}
