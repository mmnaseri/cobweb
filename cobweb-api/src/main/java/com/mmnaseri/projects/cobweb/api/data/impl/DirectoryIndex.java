package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.common.ParameterizedTypeReference;
import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.api.data.impl.nio.ObjectInputOutputManager;

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
public class DirectoryIndex<I extends Serializable & Comparable<I>, V extends Serializable> implements Index<I, V> {

    private final Path root;
    private final ObjectInputOutputManager<V> ioManager;

    public DirectoryIndex(Path root, ObjectInputOutputManager<V> ioManager) throws IOException {
        Files.createDirectories(root);
        this.root = root;
        this.ioManager = ioManager;
    }

    @Override
    public void store(I key, V value) throws IOException {
        write(key, value);
    }

    @Override
    public boolean has(I key) {
        return Files.exists(getPath(key));
    }

    @Override
    public V get(I key) throws IOException {
        return read(key);
    }

    @Override
    public boolean delete(I key) {
        try {
            return has(key) && Files.deleteIfExists(getPath(key));
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean update(I key, V value) throws IOException {
        if (!has(key)) {
            return false;
        }
        delete(key);
        write(key, value);
        return true;
    }

    @Override
    public boolean upsert(I key, V value) throws IOException {
        write(key, value);
        return true;
    }

    @Override
    public List<V> all() {
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

    private Path getPath(I key) {
        return root.resolve(String.valueOf(key));
    }

    private V read(I key) throws IOException {
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

    private void write(I key, V value) throws IOException {
        ioManager.getObjectWriter().write(getPath(key), value);
    }

}
