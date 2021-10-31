import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("org.shipkit.java") version "2.3.5"
    id("com.github.ben-manes.versions") version "0.39.0"
}

tasks.withType<DependencyUpdatesTask> {
    gradleReleaseChannel = "current"
    rejectVersionIf {
        candidate.version.contains("""[-.]M\d+""".toRegex()) ||
                candidate.version.contains("RC")
    }
}

subprojects {
    apply<JavaBasePlugin>()
    apply(plugin = "maven-publish")

    group = "com.skaggsm.ortools"

    repositories {
        mavenCentral()
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    configure<PublishingExtension> {
        repositories {
            mavenLocal()
            maven {
                name = "Personal"
                url = uri("https://maven.skaggsm.com/releases")
                credentials {
                    username = "deploy"
                    password = System.getenv("MAVEN_TOKEN")
                }
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }
}
