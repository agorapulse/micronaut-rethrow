# Micronaut Rethrow

Simple around advice which catches errors of given type and rethrows them
as another type.

## Installation

```
repositories {
    jcenter()
}

dependencies {
    compile 'com.agorapulse:micronaut-rethrow:0.1.0'
}
```

## Usage

Annotate a service method with the `@Rethrow` annotation to wrap the exceptions thrown inside the method
into a different one.

```java
@Singleton
public class MyService {

    @Rethrow(
        // rethrow exceptions as IllegalStateException
        as = IllegalStateException.class,
        // optional message
        message = "Something wrong happened",
        // optional filter
        only = IllegalArgumentException.class
    )
    void doSomeWork() {
        // throws IllegalArgumentException
    }
    
    // you can declare a function which does the conversion for you
    // other annotations's properties are ingored in that case
    @Rethrow(RethrowFuction.class)
    void doOtherWork() {
        // throws IllegalArgumentException
    }

}

public class RethrowFunction implements Function<Throwable, RuntimeException> {
    
    public RuntimeException apply(Throwable th) {
        return new IllegalStateException("Rethrown using function", th);
    }

}
```

If you are using Groovy code you can use `Closure` to declare the function:

```groovy
@Singleton
class OtherService {

    @Rethrow({ new IllegalStateException(it)})
    void doSomething() {
        // exceptional code
    } 

} 
```