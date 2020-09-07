import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    `java-library`
    kotlin("jvm") version "1.4.0"
    id("com.github.ben-manes.versions") version "0.30.0"
}

val bundle: Configuration by configurations.creating

dependencies {
    implementation("com.google.protobuf:protobuf-java:3.12.2")
    implementation("com.skaggsm:classpath-resource-extractor:0.2.0")
    api(files("libs/com.google.ortools.jar"))
    bundle(files("libs/com.google.ortools.jar"))

    testRuntimeOnly(project(":ortools-natives-all"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0-RC1")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.2.3")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.2.3")
    testImplementation("org.slf4j:slf4j-nop:2.0.0-alpha1")
}

tasks {
    jar {
        from(bundle.map { if (it.isDirectory) it else zipTree(it) })
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    test {
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
    javadoc {
        options {
            (this as StandardJavadocDocletOptions).tags(
                "apiNote:a:API Note:",
                "implSpec:a:Implementation Requirements:",
                "implNote:a:Implementation Note:"
            )
        }
    }
}
