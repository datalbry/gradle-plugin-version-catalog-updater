# Gradle Catalog Updater

The `Gradle Catalog Updater` is a sophisticated gradle plugin, helping developers to update their gradle version catalogs.

The plugin resolves the latest versions of each and every dependency by using the currently configured repositories.

> **NOTE:** The Plugin only supports the external TOML format, which is being described [here](https://docs.gradle.org/current/userguide/platforms.html).

## Getting Started

Setting up the plugin requires the following steps:

1. Add the Plugin to your `build.gradle.kts`
    ```kotlin
    plugins {
      id("io.datalbry.catalog.updater") version "<version>"
    }
    ``` 
2. Configure the Plugin 
    ```kotlin
    catalogUpdater {
      from = "./gradle/libs.versions.toml"
      to = "./gradle/updatedLibs.versions.toml" 
    }
    ```

> **NOTE:** The latest versions can be found [here](https://github.com/datalbry/gradle-version-catalog-updater/tags).  


## License
>Copyright 2021 DataLbry Technologies UG
>
>Licensed under the Apache License, Version 2.0 (the "License");
>you may not use this file except in compliance with the License.
>You may obtain a copy of the License at
>
>http://www.apache.org/licenses/LICENSE-2.0
>
>Unless required by applicable law or agreed to in writing, software
>distributed under the License is distributed on an "AS IS" BASIS,
>WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
>See the License for the specific language governing permissions and
>limitations under the License.

