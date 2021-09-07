plugins {
    id("datalbry.kotlin")
    id("datalbry.plugin")
    id("java-gradle-plugin")
    id("maven-publish")
}

val projectVersion: String by project
version = projectVersion

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.stdlib)
    implementation(libs.org.tomlj)
    implementation(libs.ben.manes.gradle.plugin)

    testImplementation(gradleTestKit())
    testImplementation(libs.junit.jupiter.api)

    testRuntime(libs.junit.jupiter.core)
    testRuntime(libs.junit.jupiter.engine)
}

gradlePlugin {
    plugins {
        create("plugin") {
            id = "io.datalbry.catalog.updater"
            implementationClass = "io.datalbry.plugin.catalog.updater.CatalogUpdaterPlugin"
            displayName = "Version Catalog Updater"
            description = "Simple plugin to update version catalogs using a Gradle task"
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}

// ------------------------------------------------------------------------
// Gradle does not allow to programmatically access the version
// But the plugin requires the version to add the correct versions of the connector-sdk preemptively
// The most simplistic solution is simply to write a properties file, whenever compiling the plugin,
// which then can be consumed by the plugin
val values = tasks.create<Copy>("writeVersionPropertiesToResources") {
    from("templates/version.properties")
    into("src/main/resources")
    expand(project.properties)
}

tasks.findByName("compileKotlin")!!.dependsOn(values)
// ------------------------------------------------------------------------
