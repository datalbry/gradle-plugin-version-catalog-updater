package io.datalbry.plugin.catalog.updater.checker

import io.datalbry.plugin.catalog.updater.model.Catalog

/**
 * [UpdatesChecker] checks [Catalog]s for updates
 *
 * @author timo gruen - 2021-09-06
 */
interface UpdatesChecker {

    /**
     * Updates all [Catalog.versions] and [Catalog.libraries] to their latest versions
     *
     * @param catalog to check
     *
     * @return the same catalog, but with updated versions
     */
    fun updateToLatest(catalog: Catalog): Catalog

}
