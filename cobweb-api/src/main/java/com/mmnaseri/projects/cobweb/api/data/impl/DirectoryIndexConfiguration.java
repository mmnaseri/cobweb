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
@SuppressWarnings("WeakerAccess")
public class DirectoryIndexConfiguration<K extends Serializable & Comparable<K>> implements IndexConfiguration {

    private static final long serialVersionUID = 8454706386779973786L;
    private final Path root;
    private final ObjectInputOutputManager objectInputOutputManager;
    private final Stringifier<K> stringifier;

    private DirectoryIndexConfiguration(Path root, ObjectInputOutputManager objectInputOutputManager, Stringifier<K> stringifier) {
        this.root = root;
        this.objectInputOutputManager = objectInputOutputManager;
        this.stringifier = stringifier;
    }

    public Path getRoot() {
        return root;
    }

    public ObjectInputOutputManager getObjectInputOutputManager() {
        return objectInputOutputManager;
    }

    public Stringifier<K> getStringifier() {
        return stringifier;
    }

    @SuppressWarnings("unused")
    public static <K extends Serializable & Comparable<K>> RootPathConfigurer<K> forKeyType(Class<K> keyType) {
        return forKeyType(new ParameterizedTypeReference<K>() {
        });
    }

    @SuppressWarnings("unused")
    public static <K extends Serializable & Comparable<K>, V> RootPathConfigurer<K> forKeyType(ParameterizedTypeReference<K> keyType) {
        return path -> inputOutputManager -> stringifier -> () ->
                new DirectoryIndexConfiguration<>(path, inputOutputManager, stringifier);
    }

    public interface RootPathConfigurer<K extends Serializable & Comparable<K>> {

        IOManagerConfigurer<K> andRootPath(Path path);

    }

    public interface IOManagerConfigurer<K extends Serializable & Comparable<K>> {

        StringifierConfigurer<K> andIOManager(ObjectInputOutputManager inputOutputManager);

    }

    public interface StringifierConfigurer<K extends Serializable & Comparable<K>> {

        DirectoryIndexConfigurationBuilder<K> andStringifier(Stringifier<K> stringifier);

    }

    public interface DirectoryIndexConfigurationBuilder<K extends Serializable & Comparable<K>> {

        DirectoryIndexConfiguration<K> build();

    }

}
