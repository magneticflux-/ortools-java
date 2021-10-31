import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    `java-library`
    kotlin("jvm") version "1.5.31"
}

val bundle: Configuration by configurations.creating

dependencies {
    api("com.google.ortools:ortools-java:9.1.9490")

    testRuntimeOnly(project(":ortools-natives-all"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.3")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.6.3")
    testImplementation("org.slf4j:slf4j-nop:2.0.0-alpha5")
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
