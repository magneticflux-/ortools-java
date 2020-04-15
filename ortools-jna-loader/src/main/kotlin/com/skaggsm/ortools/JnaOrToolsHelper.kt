package com.skaggsm.ortools

import com.sun.jna.Platform
import java.io.FileNotFoundException
import java.net.URI
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes


/**
 * Created by Mitchell Skaggs on 4/11/2020.
 */
object JnaOrToolsHelper {
    /**
     * Call before using Google OR-Tools.
     */
    @JvmStatic
    fun loadLibrary() {
        val path = extractResourcesToTempDirectory("${Platform.RESOURCE_PREFIX}/", "ortools-java", this.javaClass)
        System.load(path.resolve(Platform.RESOURCE_PREFIX).resolve(System.mapLibraryName("jniortools")).toString())
    }

    private fun extractResourcesToTempDirectory(
        source: String,
        prefix: String,
        loader: ClassLoader = ClassLoader.getSystemClassLoader()
    ): Path {
        val tempFile = Files.createTempDirectory(prefix)
        tempFile.deleteOnExit()
        extractResources(source, tempFile, loader)
        return tempFile
    }

    private fun extractResourcesToTempDirectory(
        source: String,
        prefix: String,
        `class`: Class<*>
    ): Path {
        return extractResourcesToTempDirectory(source, prefix, `class`.classLoader)
    }

    private fun extractResources(
        source: String,
        targetPath: Path,
        `class`: Class<*>
    ) {
        extractResources(source, targetPath, `class`.classLoader)
    }

    private fun extractResources(
        source: String,
        targetPath: Path,
        loader: ClassLoader = ClassLoader.getSystemClassLoader()
    ) {
        println("Extracting resources to $targetPath")
        return visitUriAsPath(
            (loader.getResource(source)
                ?: throw FileNotFoundException("Resource $source was not found in ClassLoader $loader")).toURI()
        ) { sourcePath ->
            Files.walkFileTree(sourcePath, object : SimpleFileVisitor<Path>() {
                override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
                    val newPath = targetPath.resolve(sourcePath.parent.relativize(file).toString())
                    Files.copy(file, newPath)
                    newPath.deleteOnExit()
                    return FileVisitResult.CONTINUE
                }

                override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
                    val newPath = targetPath.resolve(sourcePath.parent.relativize(dir).toString())
                    Files.copy(dir, newPath)
                    newPath.deleteOnExit()
                    return FileVisitResult.CONTINUE
                }
            })
        }
    }

    private fun visitUriAsPath(uri: URI, visitor: (path: Path) -> Unit) {
        try {
            val p: Path = Paths.get(uri)
            visitor(p)
        } catch (ex: FileSystemNotFoundException) {
            FileSystems.newFileSystem(
                uri, mapOf<String, Any>()
            ).use { fs ->
                val p = fs.provider().getPath(uri)
                visitor(p)
            }
        }
    }

}

private fun Path.deleteOnExit() {
    this.toFile().deleteOnExit()
}
