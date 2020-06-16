# ortools-java

Packages [Google OR-Tools](https://github.com/google/or-tools) as a self-extracting jar file at Maven coordinates `com.skaggsm.ortools:ortools-natives-all:7.7.7810`.

## How to Use

1. Add my Bintray repository at `https://dl.bintray.com/magneticflux/maven` to resolve the artifacts
2. Add a dependency on `com.skaggsm.ortools:ortools-natives-all:7.7.7810` in your build tool of choice
3. Write code:
```java
// Somewhere before using OR-Tools classes
OrToolsHelper.loadLibrary();
```

Before using, ensure the system meets the [minimum requirements for installing OR-Tools](https://developers.google.com/optimization/install).

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
