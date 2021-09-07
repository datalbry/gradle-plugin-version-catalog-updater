enableFeaturePreview("VERSION_CATALOGS")

rootProject.name = "catalog-updater-plugin"

includeBuild("gradle/convention")

include(
    "plugin",
)

pluginManagement {
    plugins {
        val kotlinVersion = "1.4.31"

        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.spring") version kotlinVersion apply false
    }
}
