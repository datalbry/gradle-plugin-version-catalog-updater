package io.datalbry.plugin.catalog.updater.writer

import io.datalbry.plugin.catalog.updater.model.Bundle
import io.datalbry.plugin.catalog.updater.model.Catalog
import io.datalbry.plugin.catalog.updater.model.Library
import java.io.File
import java.nio.charset.StandardCharsets

/**
 * [TomlCatalogWriter] is a TOML specific implementation for the [CatalogWriter]
 *
 * Toml is the standard format which is being supported for the gradle version catalog.
 * By the very moment of the implementation, there is no other format supported by gradle.
 *
 * @author timo gruen - 2021-09-06
 */
class TomlCatalogWriter : CatalogWriter {

    override fun writeToFile(file: File, catalog: Catalog) {
        val stringBuilder = StringBuilder()
        stringBuilder.appendLine("[versions]")
        catalog.versions
            .sortedBy { it.key }
            .onEach { stringBuilder.appendLine("${it.key} = \"${it.version}\"") }
        stringBuilder.appendLine()

        stringBuilder.appendLine("[libraries]")
        catalog.libraries
            .sortedBy { it.key }
            .onEach { stringBuilder.appendLine(it.toTomlString()) }
        stringBuilder.appendLine()

        stringBuilder.appendLine("[bundles]")
        catalog.bundles
            .sortedBy { it.key }
            .onEach { stringBuilder.appendLine(it.toTomlString()) }
        stringBuilder.appendLine()
        file.writeText(stringBuilder.toString(), StandardCharsets.UTF_8)
    }

}

private fun Bundle.toTomlString(): String {
    val modules = this.modules.map { "\"$it\"" }.joinToString { "," }
    return "$key = [$modules]"
}

private fun Library.toTomlString(): String {
    val versionRefString = versionRef?.let { ", version.ref = \"$it\"" } ?: ""
    val versionString = version?.let { ", version = \"$it\"" } ?: ""
    return "$key = { module = \"$module\" $versionRefString $versionString }"
}
