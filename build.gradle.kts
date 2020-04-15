plugins {
    id("org.shipkit.java") version "2.3.1"
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
