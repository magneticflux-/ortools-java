package com.skaggsm.ortools;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;


/**
 * Helper to set up and load OR-Tools.
 */
public final class OrToolsHelper {
    private static final String RESOURCE_PREFIX;
    private static final String[] FILES_TO_EXTRACT;
    private static boolean loaded = false;

    static {
        String resourceSuffix = System.mapLibraryName("test").split("test\\.")[1];
        switch (resourceSuffix) {
            case "dll":
                RESOURCE_PREFIX = "win32-x86-64";
                FILES_TO_EXTRACT = new String[]{"jniortools"};
                break;
            case "so":
                RESOURCE_PREFIX = "linux-x86-64";
                FILES_TO_EXTRACT = new String[]{"libjniortools", "libortools"};
                break;
            case "dylib":
                RESOURCE_PREFIX = "darwin-x86-64";
                FILES_TO_EXTRACT = new String[]{"libjniortools", "libortools"};
                break;
            default:
                throw new UnsupportedOperationException(String.format("Unknown library suffix %s!", resourceSuffix));
        }
    }

    private OrToolsHelper() {
    }

    /**
     * Call before using Google OR-Tools.
     *
     * @param klass The {@link Class} to load resources from
     */
    public static synchronized void loadLibrary(Class<?> klass) {
        if (loaded)
            return;
        Path path = extractLibrary(HasResources.build(klass));
        System.load(path.resolve(System.mapLibraryName("jniortools")).toString());
        loaded = true;
    }

    /**
     * Call before using Google OR-Tools.
     *
     * @param classLoader The {@link ClassLoader} to load resources from
     */
    public static synchronized void loadLibrary(ClassLoader classLoader) {
        if (loaded)
            return;
        Path path = extractLibrary(HasResources.build(classLoader));
        System.load(path.resolve(System.mapLibraryName("jniortools")).toString());
        loaded = true;
    }

    /**
     * Call before using Google OR-Tools.
     *
     * @implNote This method calls {@link OrToolsHelper#loadLibrary(ClassLoader)} with the classloader that loaded this class.
     */
    public static synchronized void loadLibrary() {
        loadLibrary(OrToolsHelper.class.getClassLoader());
    }

    private static synchronized Path extractLibrary(HasResources classOrClassloader) {
        try {
            Path tempPath = Files.createTempDirectory("ortools-java");
            tempPath.toFile().deleteOnExit();

            for (String file : FILES_TO_EXTRACT) {
                String fullFile = System.mapLibraryName(file);
                String fullPath = String.format("ortools-%s/%s", RESOURCE_PREFIX, fullFile);
                URL url = Objects.requireNonNull(
                        classOrClassloader.getResource(fullPath),
                        String.format("Resource \"%s\" was not found in location \"%s\"", fullPath, classOrClassloader)
                );
                try (InputStream lib = url.openStream()) {
                    Files.copy(lib, tempPath.resolve(fullFile));
                }
            }
            return tempPath;
        } catch (IOException e) {
            throw new RuntimeException("I/O error while extracting natives!", e);
        }
    }
}
