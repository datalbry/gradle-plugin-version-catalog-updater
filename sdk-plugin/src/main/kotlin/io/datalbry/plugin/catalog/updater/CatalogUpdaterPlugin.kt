package io.datalbry.plugin.catalog.updater

import org.gradle.api.Plugin
import org.gradle.api.Project

class CatalogUpdaterPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        setupExtensions(project)
        setupTasks(project)
    }

    private fun setupExtensions(project: Project) {
        project.extensions.create(EXTENSION_NAME, CatalogUpdaterPluginExtension::class.java, project)
    }

    private fun setupTasks(project: Project) {
        project.tasks.register("updateCatalog", CatalogUpdateTask::class.java)
    }

    companion object {
        const val EXTENSION_NAME = "catalogUpdater"
    }
}
