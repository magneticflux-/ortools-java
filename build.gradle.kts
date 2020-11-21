plugins {
    id("org.shipkit.java") version "2.3.5"
    id("com.github.ben-manes.versions") version "0.36.0"
}

subprojects {
    apply<JavaBasePlugin>()

    group = "com.skaggsm.ortools"

    repositories {
        jcenter()
        maven("https://dl.bintray.com/magneticflux/maven")
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}
