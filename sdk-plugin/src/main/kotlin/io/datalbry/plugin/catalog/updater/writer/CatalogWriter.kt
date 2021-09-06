package io.datalbry.plugin.catalog.updater.writer

import io.datalbry.plugin.catalog.updater.model.Catalog
import java.io.File

/**
 * [CatalogWriter] is a convenience entry point for writing [Catalog]s to [File]s
 *
 * The concrete implementation decides on the format.
 * Currently, supported formats are:
 *
 * - Toml via [TomlCatalogWriter]
 *
 * @author timo gruen - 2021-09-06
 */
interface CatalogWriter {

    /**
     * Writes the [Catalog] to the [File]
     *
     * @param file to write the catalog to
     * @param catalog to write
     */
    fun writeToFile(file: File, catalog: Catalog)

}
