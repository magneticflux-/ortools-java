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
    @JvmOverloads
    fun loadLibrary(clazz: Class<*> = this.javaClass) {
        return loadLibrary(clazz.classLoader)
    }

    /**
     * Call before using Google OR-Tools.
     */
    @JvmStatic
    fun loadLibrary(loader: ClassLoader) {
        val path = ClasspathUtils.extractResourcesToTempDirectory(
            "${Platform.RESOURCE_PREFIX}/",
            "ortools-java",
            loader
        )
        System.load(path.resolve(Platform.RESOURCE_PREFIX).resolve(System.mapLibraryName("jniortools")).toString())
    }
}
