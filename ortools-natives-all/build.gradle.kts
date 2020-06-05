plugins {
    `java-library`
}

dependencies {
    api(project(":ortools-natives-linux"))
    api(project(":ortools-natives-macos"))
    api(project(":ortools-natives-windows"))
}
