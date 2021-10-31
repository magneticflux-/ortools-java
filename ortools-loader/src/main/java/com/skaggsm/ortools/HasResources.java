package com.skaggsm.ortools;

import java.net.URL;

public interface HasResources {
    static HasResources build(Class<?> klass) {
        return klass::getResource;
    }

    static HasResources build(ClassLoader classLoader) {
        return classLoader::getResource;
    }

    URL getResource(String name);
}
