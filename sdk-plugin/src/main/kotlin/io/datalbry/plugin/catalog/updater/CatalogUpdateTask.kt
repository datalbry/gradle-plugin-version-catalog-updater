package io.datalbry.plugin.catalog.updater

import io.datalbry.plugin.catalog.updater.checker.VersionsPluginBackedUpdatesChecker
import io.datalbry.plugin.catalog.updater.reader.TomlCatalogReader
import io.datalbry.plugin.catalog.updater.writer.TomlCatalogWriter
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Task to update a catalog file
 */
class CatalogUpdateTask: DefaultTask() {

    private val updater = VersionsPluginBackedUpdatesChecker(project)
    private val reader = TomlCatalogReader()
    private val writer = TomlCatalogWriter()

    @TaskAction
    fun publish() {
        val extension = project.extensions.getByType(CatalogUpdaterPluginExtension::class.java)
        val from = File(extension.from)
        val to = File(extension.to)

        val catalog = reader.parse(from)
        val updatedCatalog = updater.updateToLatest(catalog)
        writer.writeToFile(to, updatedCatalog)
    }

}
