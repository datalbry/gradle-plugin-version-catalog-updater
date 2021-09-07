package io.datalbry.plugin.catalog.updater.reader

import io.datalbry.plugin.catalog.updater.model.Catalog
import java.io.File

/**
 * [CatalogParser] is a simple parser interface, making it easy to read a specific
 *
 *  @author timo gruen - 2021-09-07
 */
interface CatalogParser {

    /**
     * Parse a Catalog by file
     *
     * @param toml to serialize the catalog from
     */
    fun parse(toml: File): Catalog

    /**
     * Parse a Catalog by String
     *
     * @param text to serialize the catalog from
     */
    fun parse(text: String): Catalog
}
