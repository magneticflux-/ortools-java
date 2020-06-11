package com.skaggsm.ortools

import com.skaggsm.ClasspathUtils
import com.sun.jna.Platform
import java.nio.file.Path


/**
 * Helper to set up and load OR-Tools.
 */
object OrToolsHelper {

    /**
     * Call before using Google OR-Tools.
     */
    @JvmStatic
    fun loadLibrary(`class`: Class<*>) {
        val path = extractLibrary(`class`)
        System.load(path.resolve(Platform.RESOURCE_PREFIX).resolve(System.mapLibraryName("jniortools")).toString())
    }

    /**
     * Call before using Google OR-Tools.
     */
    @JvmStatic
    @JvmOverloads
    fun loadLibrary(classLoader: ClassLoader = this.javaClass.classLoader) {
        val path = extractLibrary(classLoader)
        System.load(path.resolve(Platform.RESOURCE_PREFIX).resolve(System.mapLibraryName("jniortools")).toString())
    }

    private fun extractLibrary(classOrClassloader: Any): Path {
        return when (classOrClassloader) {
            is Class<*> -> ClasspathUtils.extractResourcesToTempDirectory(
                "${Platform.RESOURCE_PREFIX}/", "ortools-java", classOrClassloader
            )
            is ClassLoader -> ClasspathUtils.extractResourcesToTempDirectory(
                "${Platform.RESOURCE_PREFIX}/", "ortools-java", classOrClassloader
            )
            else -> throw IllegalArgumentException("Not passed a class or classloader!")
        }
    }
}
