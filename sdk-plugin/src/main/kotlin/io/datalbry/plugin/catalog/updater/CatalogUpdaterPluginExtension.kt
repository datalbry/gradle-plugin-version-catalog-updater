package io.datalbry.plugin.catalog.updater

import io.datalbry.plugin.catalog.updater.extensions.propertyOrDefault
import org.gradle.api.Project
import javax.inject.Inject

abstract class CatalogUpdaterPluginExtension @Inject constructor(private val project: Project) {

    var from: String = project.propertyOrDefault(
        "catalog.updater.from",
        "${project.rootProject.path}/gradle/libs.versions.toml")
    var to: String = project.propertyOrDefault(
        "catalog.updater.to",
        from)

}
