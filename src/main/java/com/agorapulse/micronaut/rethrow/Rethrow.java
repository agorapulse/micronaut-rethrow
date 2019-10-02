package com.agorapulse.micronaut.rethrow;

import io.micronaut.aop.Around;
import io.micronaut.context.annotation.Type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.function.Function;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Rethrows an exception as different one.
 *
 * You should set the <code>value</code> or the <code>as</code> property otherwise
 * {@link RuntimeException} used as wrapper function.
 *
 * If <code>value</code> is set then <code>as</code> is ignored.
 */
@Documented
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Around
@Type(RethrowInterceptor.class)
public @interface Rethrow {

    /**
     * @return Function or closure which coverts the thrown exception to new one. New exception must extend {@link RuntimeException}
     */
    Class<? extends Function<? extends Throwable, ? extends RuntimeException>> value() default RethrowAsRuntimeException.class;

    /**
     * @return Type of the {@link RuntimeException} wrapping the original cause. The exception class must have constructor which accepts {@link Throwable}
     */
    Class<? extends RuntimeException> as() default RuntimeException.class;

    /**
     * @return only handle exceptions assignable to given type
     */
    Class<? extends Throwable>[] only() default Throwable.class;

    /**
     * @return Additional message for the rethrown {@link RuntimeException}. The exception class must have constructor accepting two arguments ({@link String} and {@link Throwable}
     */
    String message() default "";

}
