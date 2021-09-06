import io.datalbry.plugin.catalog.updater.CatalogUpdaterPluginExtension

plugins {
    id("io.datalbry.connector.sdk")
}

tasks.register<DefaultTask>("assertExtensionRespectsDefaults") {
    doLast {
        if (project.extensions.getByType(CatalogUpdaterPluginExtension::class.java).version == "1.8") {
            throw GradleException("Failed")
        }
    }
    outputs.upToDateWhen { false }
}
