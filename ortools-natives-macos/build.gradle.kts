plugins {
    `java-library`
}

dependencies {
    api(project(":ortools-loader"))
    api("com.google.ortools:ortools-win32-x86-64:9.1.9490")
}
