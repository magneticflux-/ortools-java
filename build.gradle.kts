plugins {
    java
    kotlin("jvm") version "1.3.71"
    id("org.shipkit.java") version "2.3.1"
    id("com.github.ben-manes.versions") version "0.28.0"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "com.skaggsm"

repositories {
    jcenter()
}

val bundle: Configuration by configurations.creating

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("net.java.dev.jna:jna:5.5.0")
    implementation("com.google.protobuf:protobuf-java:3.11.4")
    api(files("libs/com.google.ortools.jar"))
    bundle(files("libs/com.google.ortools.jar"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.0.2")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.0.2")
    testImplementation("org.slf4j:slf4j-nop:2.0.0-alpha1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.jar {
    from(bundle.map { if (it.isDirectory) it else zipTree(it) })
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events(
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
        )
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

tasks.javadoc {
    options {
        (this as StandardJavadocDocletOptions).tags(
            "apiNote:a:API Note:",
            "implSpec:a:Implementation Requirements:",
            "implNote:a:Implementation Note:"
        )
    }
}
