package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.common.ParameterizedTypeReference;
import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.api.data.Stringifier;
import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectInputOutputManager;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/8/17, 8:04 PM)
 */
public class DirectoryIndex<K extends Serializable & Comparable<K>, V extends Serializable> implements Index<K, V> {

    private static final long serialVersionUID = -1848479875693303556L;
    private final Path root;
    private final Stringifier<K> stringifier;
    private final ObjectInputOutputManager ioManager;

    public DirectoryIndex(Path root, Stringifier<K> stringifier, ObjectInputOutputManager ioManager) throws IOException {
        this.stringifier = stringifier;
        Files.createDirectories(root);
        this.root = root;
        this.ioManager = ioManager;
    }

    @Override
    public boolean store(K key, V value) throws IOException {
        if (has(key)) {
            return false;
        }
        write(key, value);
        return true;
    }

    @Override
    public boolean store(K key) throws IOException {
        return store(key, null);
    }

    @Override
    public boolean has(K key) {
        return Files.exists(getPath(key));
    }

    @Override
    public V get(K key) throws IOException {
        return read(key);
    }

    @Override
    public boolean delete(K key) throws IOException {
        return has(key) && Files.deleteIfExists(getPath(key));
    }

    @Override
    public boolean update(K key, V value) throws IOException {
        if (!has(key)) {
            return false;
        }
        delete(key);
        write(key, value);
        return true;
    }

    @Override
    public boolean upsert(K key, V value) throws IOException {
        write(key, value);
        return true;
    }

    @Override
    public List<V> values() {
        return list().map(this::read).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return list().count();
    }

    @Override
    public void truncate() {
        final Boolean allFilesDeleted = list().map(this::deleteFile)
                .reduce(Boolean::logicalOr)
                .orElse(Boolean.TRUE);
        if (!allFilesDeleted) {
            throw new IllegalStateException();
        }
    }

    @Override
    public List<K> keys() {
        return list()
                .map(Path::getFileName)
                .map(Path::toString)
                .map(stringifier::fromString)
                .collect(Collectors.toList());
    }

    private Boolean deleteFile(Path path) {
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            return false;
        }
    }

    private Stream<Path> list() {
        try {
            return Files.list(root);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to list contents of " + root);
        }
    }

    private Path getPath(K key) {
        return root.resolve(stringifier.toString(key));
    }

    private V read(K key) throws IOException {
        return ioManager.getObjectReader().read(getPath(key), new ParameterizedTypeReference<V>() {
        });
    }

    private V read(Path path) {
        try {
            return ioManager.getObjectReader().read(path, new ParameterizedTypeReference<V>() {
            });
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void write(K key, V value) throws IOException {
        ioManager.getObjectWriter().write(getPath(key), value);
    }

}
