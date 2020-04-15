plugins {
    `java-library`
}

dependencies {
    runtimeOnly(project(":ortools-jna-loader"))
}

//bintrayUpload.enabled = true
