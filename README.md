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

```java
@Singleton
public class MyService {

    @Rethrow(
        // rethrow as IllegalStateException
        as = IllegalStateException.class,
        // opi
        message = 
    )
    void doSomeWork() {
        // throws CustomRuntimeException
    }

}
```