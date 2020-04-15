plugins {
    `java-library`
}

dependencies {
    runtimeOnly(project(":ortools-natives-linux"))
    runtimeOnly(project(":ortools-natives-macos"))
    runtimeOnly(project(":ortools-natives-windows"))
}
