# ortools-java

Packages [Google OR-Tools](https://github.com/google/or-tools) as a self-extracting jar file at Maven coordinates `com.skaggsm.ortools:ortools-natives-all:7.7.7810`.

## How to Use

1. Add the JCenter repository to resolve the artifacts
2. Add a dependency on `com.skaggsm.ortools:ortools-natives-all:7.7.7810` in your build tool of choice
3. Write code:
```java
// Somewhere before using OR-Tools classes
OrToolsHelper.loadLibrary();
```

Before using, ensure the system meets the [minimum requirements for installing OR-Tools](https://developers.google.com/optimization/install).

## Supported Java Versions

### Simple Classpaths

Basic requirements: >= Java 8

### Complex Classpaths (Spring, etc.)

Basic requirements: >= Java 11

Confirmed working versions:

- Java 11
  - `OpenJDK Runtime Environment 18.9 (build 11.0.7+10)`
  - `OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.7+10)`
  - `OpenJDK Runtime Environment GraalVM CE 20.1.0 (build 11.0.7+10-jvmci-20.1-b02)`
- Java 12
  - `OpenJDK Runtime Environment (build 12.0.2+10)`
  - `OpenJDK Runtime Environment AdoptOpenJDK (build 12.0.2+10)`
- Java 13
  - `OpenJDK Runtime Environment (build 13.0.2+8)`
  - `OpenJDK Runtime Environment AdoptOpenJDK (build 13.0.2+8)`
  - `Java(TM) SE Runtime Environment (build 13.0.2+8)`
  - Likely all versions
- Java 14
  - Likely all versions

Confirmed ***NOT*** working versions:

- Java 11
  - `Java(TM) SE Runtime Environment 18.9 (build 11.0.7+8-LTS)`

More information here: [#10](https://github.com/magneticflux-/ortools-java/issues/10).

## Details

This library is divided into several modules:
- `ortools-loader`
  - Handles extracting the relevant binaries from the classpath and loading them for JNI.
- `ortools-natives-all`
  - Meta-dependency that resolves all possible native dependencies.
- `ortools-natives-linux`
  - Packages the "Ubuntu 16.04" binary distribution
- `ortools-natives-macos`
  - Packages the "Mac OS X binary" distribution
- `ortools-natives-windows`
  - Packages the "Windows with Visual Studio 2019" binary distribution

Native library lifecycle:
1. JVM startup
2. `OrToolsHelper.loadLibrary();` called
    - Native binaries are extracted to a temp directory
    - Native binaries are loaded by the system
3. JVM shutdown
    - Native binaries are deleted
