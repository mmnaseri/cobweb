package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.data.IndexConfiguration;
import com.mmnaseri.projects.cobweb.api.data.impl.nio.ObjectInputOutputManager;

import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 8:07 PM)
 */
public class DirectoryIndexConfiguration<O> implements IndexConfiguration {

    private final Path root;
    private final ObjectInputOutputManager<O> objectInputOutputManager;

    private DirectoryIndexConfiguration(Path root, ObjectInputOutputManager<O> objectInputOutputManager) {
        this.root = root;
        this.objectInputOutputManager = objectInputOutputManager;
    }

    public Path getRoot() {
        return root;
    }

    public ObjectInputOutputManager<O> getObjectInputOutputManager() {
        return objectInputOutputManager;
    }

    public static <V> IOManagerConfigurer<V> withRootPath(Path path) {
        return inputOutputManager -> new DirectoryIndexConfiguration<>(path, inputOutputManager);
    }

    public interface IOManagerConfigurer<V> {

        DirectoryIndexConfiguration<V> andIOManager(ObjectInputOutputManager<V> inputOutputManager);

    }

}
