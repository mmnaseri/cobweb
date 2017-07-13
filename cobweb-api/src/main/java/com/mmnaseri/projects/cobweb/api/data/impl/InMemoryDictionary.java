package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.data.Dictionary;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 6:11 PM)
 */
public class InMemoryDictionary<K extends Serializable & Comparable<K>> implements Dictionary<K>, Serializable {

    private static final long serialVersionUID = -5402143614757343791L;
    private final Set<K> data = new CopyOnWriteArraySet<>();

    @Override
    public boolean store(K key) throws IOException {
        return data.add(key);
    }

    @Override
    public boolean has(K key) {
        return data.contains(key);
    }

    @Override
    public boolean delete(K key) {
        return data.remove(key);
    }

    @Override
    public long count() {
        return data.size();
    }

    @Override
    public void truncate() {
        data.clear();
    }

    @Override
    public List<K> keys() {
        return new LinkedList<>(data);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(data);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        final Object object = in.readObject();
        //noinspection unchecked
        final Set<K> set = (Set<K>) object;
        data.clear();
        data.addAll(set);
    }


}
