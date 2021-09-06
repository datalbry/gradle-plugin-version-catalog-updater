plugins {
    id("datalbry.kotlin")
    id("datalbry.publication")
    id("java-gradle-plugin")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("org.tomlj:tomlj:1.0.0")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.39.0")

    testImplementation(gradleTestKit())
    testImplementation(libs.junit.jupiter.api)
    testRuntime(libs.junit.jupiter.core)
    testRuntime(libs.junit.jupiter.engine)
}

gradlePlugin {
    plugins {
        create("connector-sdk") {
            id = "io.datalbry.catalog.updater"
            implementationClass = "io.datalbry.connector.plugin.CatalogUpdaterPlugin"
        }
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
