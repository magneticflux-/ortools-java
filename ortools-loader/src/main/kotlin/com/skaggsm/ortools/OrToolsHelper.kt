package com.skaggsm.ortools

import com.skaggsm.ClasspathUtils
import com.sun.jna.Platform


/**
 * Helper to set up and load OR-Tools.
 */
object OrToolsHelper {
    /**
     * Call before using Google OR-Tools.
     */
    @JvmStatic
    fun loadLibrary() {
        val path = ClasspathUtils.extractResourcesToTempDirectory(
            "${Platform.RESOURCE_PREFIX}/",
            "ortools-java",
            this.javaClass
        )
        System.load(path.resolve(Platform.RESOURCE_PREFIX).resolve(System.mapLibraryName("jniortools")).toString())
    }
}
