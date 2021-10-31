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
    private static final String RESOURCE_SUFFIX;
    private static final String[] FILES_TO_EXTRACT;

    static {
        RESOURCE_SUFFIX = System.mapLibraryName("test").split("test\\.")[1];
        switch (RESOURCE_SUFFIX) {
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
                throw new UnsupportedOperationException(String.format("Unknown library suffix %s!", RESOURCE_SUFFIX));
        }
    }

    private OrToolsHelper() {
    }

    /**
     * Call before using Google OR-Tools.
     *
     * @param klass The {@link Class} to load resources from
     */
    public static void loadLibrary(HasResources klass) {
        Path path = extractLibrary(klass);
        System.load(path.resolve(RESOURCE_PREFIX).resolve(System.mapLibraryName("jniortools")).toString());
    }

    /**
     * Call before using Google OR-Tools.
     *
     * @param classLoader The {@link ClassLoader} to load resources from
     */
    public static void loadLibrary(ClassLoader classLoader) {
        Path path = extractLibrary((HasResources) classLoader);
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

    private static Path extractLibrary(HasResources classOrClassloader) {
        try {
            Path tempPath = Files.createTempDirectory("ortools-java");
            tempPath.toFile().deleteOnExit();

            for (String file : FILES_TO_EXTRACT) {
                String fullPath = String.format("ortools-%s/%s.%s", RESOURCE_PREFIX, file, RESOURCE_SUFFIX);
                URL url = Objects.requireNonNull(
                        classOrClassloader.getResource(fullPath),
                        String.format("Resource \"%s\" was not found in location \"%s\"", fullPath, classOrClassloader)
                );
                try (InputStream lib = url.openStream()) {
                    Files.copy(lib, tempPath.resolve(file));
                }
            }
            return tempPath;
        } catch (IOException e) {
            throw new RuntimeException("I/O error while extracting natives!", e);
        }
    }

    private interface HasResources {
        URL getResource(String name);
    }
}
