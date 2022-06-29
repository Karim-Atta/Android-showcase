apply {
    from("$rootDir/library-build.gradle")
}

plugins{
    id(SqlDelight.plugin)
    kotlin("plugin.serialization") version "1.6.21"
}

dependencies{
    "implementation"(project(Modules.heroDomain))
    "implementation"(Ktor.core)
    "implementation"(Ktor.serialization)
    "implementation"(Ktor.contentNegotiation)
    "implementation"(Ktor.android)
    "implementation"(SqlDelight.runtime)
}

sqldelight{
    database("HeroDatabase"){
        packageName = "com.karem.hero_datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}
