package io.datalbry.plugin.catalog.updater.reader

import io.datalbry.plugin.catalog.updater.model.Bundle
import io.datalbry.plugin.catalog.updater.model.Catalog
import io.datalbry.plugin.catalog.updater.model.Library
import io.datalbry.plugin.catalog.updater.model.Version
import java.io.File
import org.tomlj.Toml
import org.tomlj.TomlArray
import org.tomlj.TomlParseResult
import org.tomlj.TomlTable

/**
 * Toml specific parser for the gradle version catalog
 *
 * @author timo gruen 2021-09-06
 */
class TomlCatalogReader: CatalogParser {

    override fun parse(toml: File): Catalog {
        val parseResult = Toml.parse(toml.toPath())
        return parse(parseResult)
    }

    override fun parse(text: String): Catalog {
        val parseResult = Toml.parse(text)
        return parse(parseResult)
    }

    private fun parse(toml: TomlParseResult): Catalog {
        val versions = toml.getVersions()
        val libraries = toml.getLibraries()
        val bundles = toml.getBundles()
        return Catalog(versions, libraries, bundles)
    }

    private fun TomlParseResult.getVersions(): Set<Version> {
        return (this["versions"] as TomlTable)
            .toMap()
            .toMap()
            .entries
            .filterIsInstance<Map.Entry<String, String>>()
            .map { it.toVersion() }
            .toSet()
    }

    private fun TomlParseResult.getLibraries(): Set<Library> {
        return (this["libraries"] as TomlTable)
            .toMap()
            .entries
            .filterIsInstance<Map.Entry<String, TomlTable>>()
            .map { it.toLibrary() }
            .toSet()
    }

    private fun TomlParseResult.getBundles(): Set<Bundle> {
        return (this["bundles"] as TomlTable)
            .toMap()
            .entries
            .filterIsInstance<Map.Entry<String, TomlTable>>()
            .map { it.toBundle() }
            .toSet()
    }

}

fun Map.Entry<String, String>.toVersion(): Version {
    val key = key
    val version = value
    return Version(key, version)
}

fun Map.Entry<String, TomlTable>.toLibrary(): Library {
    val key = key
    val module = value.get("module") as String
    val versionRef = value.get("version.ref") as String?
    return Library(key, module, versionRef)
}

fun Map.Entry<String, TomlTable>.toBundle(): Bundle {
    val key = key
    val references = (value as TomlArray).toList().filterIsInstance<String>()
    return Bundle(key, references.toSet())
}
