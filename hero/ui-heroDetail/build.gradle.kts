apply{
    from("$rootDir/android-library-build.gradle")
}

dependencies{

    "implementation"(project(Modules.components))
    "implementation"(project(Modules.heroInteractors))
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.heroDomain))

    "implementation"(Coil.coil)


    "androidTestImplementation"(project(Modules.heroDataSourceTest))
    "androidTestImplementation"(ComposeTest.uiTestJunit4)
    "debugImplementation"(ComposeTest.uiTestManifest)
    "androidTestImplementation"(Junit.junit4)

}

