package com.skaggsm.ortools;

import com.skaggsm.ClasspathUtils;

import java.io.IOException;
import java.nio.file.Path;


/**
 * Helper to set up and load OR-Tools.
 */
public final class OrToolsHelper {
    private final static String RESOURCE_PREFIX;

    static {
        String libraryName = System.mapLibraryName("x");

        if (libraryName.endsWith("dll"))
            RESOURCE_PREFIX = "win32-x86-64";
        else if (libraryName.endsWith("dylib"))
            RESOURCE_PREFIX = "darwin";
        else if (libraryName.endsWith("so"))
            RESOURCE_PREFIX = "linux-x86-64";
        else
            throw new Error("Unable to determine resource prefix! This environment may not be supported.");
    }

    private OrToolsHelper() {
    }

    /**
     * Call before using Google OR-Tools.
     *
     * @param klass The {@link Class} to load resources from
     */
    public static void loadLibrary(Class<?> klass) {
        Path path = extractLibrary(klass);
        System.load(path.resolve(RESOURCE_PREFIX).resolve(System.mapLibraryName("jniortools")).toString());
    }

    /**
     * Call before using Google OR-Tools.
     *
     * @param classLoader The {@link ClassLoader} to load resources from
     */
    public static void loadLibrary(ClassLoader classLoader) {
        Path path = extractLibrary(classLoader);
        System.load(path.resolve(RESOURCE_PREFIX).resolve(System.mapLibraryName("jniortools")).toString());
    }

    /**
     * Call before using Google OR-Tools.
     *
     * @implNote This method calls {@link OrToolsHelper#loadLibrary(ClassLoader)} with the classloader that loaded this class.
     */
    public static void loadLibrary() {
        loadLibrary(OrToolsHelper.class.getClassLoader());
    }

    private static Path extractLibrary(Object classOrClassloader) {
        try {
            if (classOrClassloader instanceof Class<?>) {
                return ClasspathUtils.extractResourcesToTempDirectory(RESOURCE_PREFIX + '/', "ortools-java", (Class<?>) classOrClassloader);
            } else if (classOrClassloader instanceof ClassLoader) {
                return ClasspathUtils.extractResourcesToTempDirectory(RESOURCE_PREFIX + '/', "ortools-java", (ClassLoader) classOrClassloader);
            } else
                throw new IllegalArgumentException("Not passed a class or classloader!");
        } catch (IOException e) {
            throw new Error("Error during library extraction!", e);
        }
    }
}
