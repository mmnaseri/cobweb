package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.common.ParameterizedTypeReference;
import com.mmnaseri.projects.cobweb.api.data.IndexConfiguration;
import com.mmnaseri.projects.cobweb.api.data.Stringifier;
import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectInputOutputManager;

import java.io.Serializable;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 8:07 PM)
 */
public class DirectoryIndexConfiguration<K extends Serializable & Comparable<K>, V> implements IndexConfiguration {

    private final Path root;
    private final ObjectInputOutputManager<V> objectInputOutputManager;
    private final Stringifier<K> stringifier;

    private DirectoryIndexConfiguration(Path root, ObjectInputOutputManager<V> objectInputOutputManager, Stringifier<K> stringifier) {
        this.root = root;
        this.objectInputOutputManager = objectInputOutputManager;
        this.stringifier = stringifier;
    }

    public Path getRoot() {
        return root;
    }

    public ObjectInputOutputManager<V> getObjectInputOutputManager() {
        return objectInputOutputManager;
    }

    public Stringifier<K> getStringifier() {
        return stringifier;
    }

    public static <K extends Serializable & Comparable<K>, V> RootPathConfigurer<K, V> forIndexType(Class<K> keyType, Class<V> valueType) {
        return forIndexType(new ParameterizedTypeReference<K>() {
        }, new ParameterizedTypeReference<V>() {
        });
    }

    public static <K extends Serializable & Comparable<K>, V> RootPathConfigurer<K, V> forIndexType(ParameterizedTypeReference<K> keyType, ParameterizedTypeReference<V> valueType) {
        return path -> inputOutputManager -> stringifier -> () ->
                new DirectoryIndexConfiguration<>(path, inputOutputManager, stringifier);
    }

    public interface RootPathConfigurer<K extends Serializable & Comparable<K>, V> {

        IOManagerConfigurer<K, V> andRootPath(Path path);

    }

    public interface IOManagerConfigurer<K extends Serializable & Comparable<K>, V> {

        StringifierConfigurer<K, V> andIOManager(ObjectInputOutputManager<V> inputOutputManager);

    }

    public interface StringifierConfigurer<K extends Serializable & Comparable<K>, V> {

        DirectoryIndexConfigurationBuilder<K, V> andStringifier(Stringifier<K> stringifier);

    }

    public interface DirectoryIndexConfigurationBuilder<K extends Serializable & Comparable<K>, V> {

        DirectoryIndexConfiguration<K, V> build();

    }

}
