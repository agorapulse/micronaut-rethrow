package com.agorapulse.micronaut.rethrow;

import io.micronaut.retry.annotation.Retryable;

import javax.inject.Singleton;

@Singleton
public class RetryableService {

    private int counter;

    @Rethrow(
        only = UnsupportedOperationException.class,
        as = IllegalStateException.class
    )
    @Retryable
    void retryOnException() {
        counter++;
        throw new UnsupportedOperationException("Not Supported");
    }

    public int getCounter() {
        return counter;
    }
}
