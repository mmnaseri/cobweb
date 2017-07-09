package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.common.ParameterizedTypeReference;
import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectInputOutputManager;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/8/17, 8:04 PM)
 */
public class DirectoryIndex<I extends Serializable & Comparable<I>, V extends Serializable> implements Index<I, V> {

    private final Path root;
    private final ObjectInputOutputManager ioManager;

    public DirectoryIndex(Path root, ObjectInputOutputManager ioManager) {
        this.root = root;
        this.ioManager = ioManager;
    }

    @Override
    public void store(I key, V value) throws IOException {
        write(key, value);
    }

    @Override
    public boolean has(I key) {
        return getFile(key).exists();
    }

    @Override
    public V get(I key) throws IOException {
        return read(key);
    }

    @Override
    public boolean delete(I key) {
        return has(key) && getFile(key).delete();
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
        final File[] files = getFiles();
        return Arrays.stream(files)
                .map(this::read)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return getFiles().length;
    }

    @Override
    public void truncate() {
        final Boolean allFilesDeleted = Arrays.stream(getFiles())
                .map(File::delete)
                .reduce(Boolean::logicalAnd)
                .orElse(Boolean.TRUE);
        if (!allFilesDeleted) {
            throw new IllegalStateException();
        }
    }

    private File[] getFiles() {
        final File file = root.toFile();
        if (file == null || !file.isDirectory()) {
            throw new IllegalStateException();
        }
        return file.listFiles(pathname -> !pathname.getName().startsWith("."));
    }

    private File getFile(I key) {
        return root.resolve(String.valueOf(key)).toFile();
    }

    private V read(I key) throws IOException {
        return ioManager.getReader().read(getFile(key), new ParameterizedTypeReference<V>() {
        });
    }

    private V read(File file) {
        try {
            return ioManager.getReader().read(file, new ParameterizedTypeReference<V>() {
            });
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void write(I key, V value) throws IOException {
        ioManager.getWriter().write(getFile(key), value);
    }

}
