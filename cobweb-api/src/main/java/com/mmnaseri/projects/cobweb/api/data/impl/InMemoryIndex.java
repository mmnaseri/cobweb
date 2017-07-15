package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.data.Index;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/17, 1:06 PM)
 */
public class InMemoryIndex<K extends Serializable & Comparable<K>, V extends Serializable> implements Index<K, V> {

    private static final long serialVersionUID = 8312803062826071676L;
    private final Map<K, V> data = new ConcurrentHashMap<>();
    private final Set<K> nulls = new CopyOnWriteArraySet<>();

    @Override
    public boolean store(K key, V value) throws IOException {
        if (has(key)) {
            return false;
        }
        if (value == null) {
            nulls.add(key);
        } else {
            data.put(key, value);
        }
        return true;
    }

    @Override
    public V get(K key) throws IOException {
        return nulls.contains(key) ? null : data.get(key);
    }

    @Override
    public boolean update(K key, V value) throws IOException {
        if (!has(key)) {
            return false;
        }
        if (nulls.contains(key) && value != null) {
            nulls.remove(key);
            data.put(key, value);
        } else if (data.containsKey(key) && value == null) {
            data.remove(key);
            nulls.add(key);
        } else if (data.containsKey(key)) {
            data.put(key, value);
        }
        return true;
    }

    @Override
    public boolean upsert(K key, V value) throws IOException {
        data.remove(key);
        nulls.remove(key);
        store(key, value);
        return true;
    }

    @Override
    public List<V> values() {
        return Stream.of(
                data.values(),
                nulls.stream().map(i -> (V) null).collect(Collectors.toList())
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public boolean store(K key) throws IOException {
        if (has(key)) {
            return false;
        }
        nulls.add(key);
        return true;
    }

    @Override
    public boolean has(K key) {
        return nulls.contains(key) || data.containsKey(key);
    }

    @Override
    public boolean delete(K key) throws IOException {
        if (nulls.contains(key)) {
            return nulls.remove(key);
        }
        if (data.containsKey(key)) {
            data.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public long count() {
        return data.size() + nulls.size();
    }

    @Override
    public void truncate() {
        data.clear();
        nulls.clear();
    }

    @Override
    public List<K> keys() {
        return Stream.concat(data.keySet().stream(), nulls.stream()).collect(Collectors.toList());
    }

}
