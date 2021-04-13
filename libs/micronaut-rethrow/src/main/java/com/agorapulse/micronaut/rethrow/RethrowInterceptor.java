/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2019-2021 Agorapulse.
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
package com.agorapulse.micronaut.rethrow;

import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.annotation.AnnotationValue;

import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

@Singleton
public class RethrowInterceptor implements MethodInterceptor<Object, Object> {

    private static final RethrowAsRuntimeException DEFAULT_CONVERTER  = new RethrowAsRuntimeException();

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        try {
            return context.proceed();
        } catch (Throwable error) {
            AnnotationValue<Rethrow> annotation = context.getAnnotation(Rethrow.class);

            Class<? extends Throwable>[] only = (Class<? extends Throwable>[]) annotation.get("only", Class[].class).orElse(new Class[0]);

            if (Arrays.stream(only).noneMatch(type -> type.isAssignableFrom(error.getClass()))) {
                throw error;
            }

            Class<? extends Function<? extends Throwable, ? extends RuntimeException>> value = readClosureAwareValue(context);

            if (value != null) {
                if ("groovy.lang.Closure".equals(value.getSuperclass().getName())) {
                    try {
                        Object self = context.getTarget();
                        Object closure = value.getConstructor(Object.class, Object.class).newInstance(self, self);
                        RuntimeException result = (RuntimeException) closure.getClass().getMethod("call", Object.class).invoke(closure, error);
                        throw result;
                    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                        throw new IllegalArgumentException("Cannot instantiate closure! Type: " + value, e);
                    }
                }

                if (!RethrowAsRuntimeException.class.equals(value)) {
                    try {
                        Function<Throwable, RuntimeException> convert = (Function<Throwable, RuntimeException>) value.newInstance();
                        throw convert.apply(error);
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new IllegalArgumentException("Cannot create function for value " + value, e);
                    }
                }
            }

            Optional<Class> as = annotation.get("as", Class.class);
            if (annotation.isPresent("as") && as.isPresent()) {
                try {
                    String message = annotation.get("message", String.class).orElse("");
                    if (message.length() == 0) {
                        throw (RuntimeException) as.get().getConstructor(Throwable.class).newInstance(error);
                    }
                    throw (RuntimeException) as.get().getConstructor(String.class, Throwable.class).newInstance(message, error);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    throw new IllegalArgumentException("Cannot create function for value " + as.get(), e);
                }
            }

            throw DEFAULT_CONVERTER.apply(error);
        }
    }

    /**
     * Due the ongoing bug in Micronaut we need to use the old-fashioned way to get the annotation value.
     * @link https://github.com/micronaut-projects/micronaut-core/issues/1022
     */
    private Class<? extends Function<? extends Throwable, ? extends RuntimeException>> readClosureAwareValue(MethodInvocationContext<Object, Object> context) {
        Rethrow rethrow = context.getTargetMethod().getAnnotation(Rethrow.class);

        if (rethrow == null) {
            rethrow = context.getTargetMethod().getDeclaringClass().getAnnotation(Rethrow.class);
        }

        return rethrow.value();
    }

}
