package io.datalbry.plugin.catalog.updater.reader

import io.datalbry.plugin.catalog.updater.model.Catalog
import java.io.File

/**
 *
 */
interface CatalogParser {

    /**
     *
     */
    fun parse(toml: File): Catalog

    fun parse(text: String): Catalog
}
