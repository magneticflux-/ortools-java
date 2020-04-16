# ortools-java

Packages [Google OR-Tools](https://github.com/google/or-tools) as a self-extracting jar file at Maven coordinates `com.skaggsm.ortools:ortools-natives-all:7.5.7466`.

## How to Use

```java
// Somewhere before using OR-Tools classes
OrToolsHelper.loadLibrary();
```

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

---

Before using, ensure the system meets the [minimum requirements for installing OR-Tools](https://developers.google.com/optimization/install).
