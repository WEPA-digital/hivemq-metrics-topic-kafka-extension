plugins {
    id("java")
    alias(libs.plugins.defaults)
    alias(libs.plugins.license)
}

group = "eu.wepa.hivemqextensions"
description = "Metric per topic customization for Kafka broker"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.hivemq.kafkaExtension.customizationSdk)
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        "test"(JvmTestSuite::class) {
            useJUnitJupiter(libs.versions.junit.jupiter)
            dependencies {
                implementation(libs.mockito)
                runtimeOnly(libs.slf4j.simple)
            }
        }
    }
}

tasks.withType<Jar>().configureEach {
    manifest.attributes(
        "Implementation-Title" to project.name,
        "Implementation-Vendor" to "WEPA Digital GmbH",
        "Implementation-Version" to project.version,
    )
}

license {
    header = rootDir.resolve("HEADER")
    mapping("java", "SLASHSTAR_STYLE")
}