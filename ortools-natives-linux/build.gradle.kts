plugins {
    `java-library`
}

dependencies {
    api(project(":ortools-loader"))
    api("com.google.ortools:ortools-linux-x86-64:9.1.9491")
}
