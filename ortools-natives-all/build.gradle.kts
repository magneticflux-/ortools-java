plugins {
    java
}

dependencies {
    implementation(project(":ortools-natives-linux"))
    implementation(project(":ortools-natives-macos"))
    implementation(project(":ortools-natives-windows"))
}

//bintrayUpload.enabled = true
