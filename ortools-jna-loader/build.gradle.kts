import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    `java-library`
    kotlin("jvm") version "1.3.72"
    id("com.github.ben-manes.versions") version "0.28.0"
}

val bundle: Configuration by configurations.creating

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("net.java.dev.jna:jna:5.5.0")
    implementation("com.google.protobuf:protobuf-java:3.11.4")
    implementation("com.skaggsm:classpath-resource-extractor:0.1.0")
    api(files("libs/com.google.ortools.jar"))
    bundle(files("libs/com.google.ortools.jar"))

    testRuntimeOnly(project(":ortools-natives-all"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.0.2")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.0.2")
    testImplementation("org.slf4j:slf4j-nop:2.0.0-alpha1")
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
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
            TestLogEvent.FAILED,
            TestLogEvent.STANDARD_ERROR,
            TestLogEvent.STANDARD_OUT
        )
        exceptionFormat = TestExceptionFormat.FULL
    }
    systemProperty("jna.debug_load", true)
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
