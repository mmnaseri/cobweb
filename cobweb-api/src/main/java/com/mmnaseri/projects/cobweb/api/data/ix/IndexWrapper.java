package com.mmnaseri.projects.cobweb.api.data.ix;

import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.IOException;
import java.io.Serializable;
import java.util.function.Function;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/17, 12:50 AM)
 */
public class IndexWrapper<K extends Serializable & Comparable<K>, V extends Serializable> {

    private final Index<K, V> index;

    public IndexWrapper(Index<K, V> index) {
        this.index = index;
    }

    public V save(K key, V value) {
        try {
            index.upsert(key, value);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return value;
    }

    public void delete(K key) {
        try {
            index.delete(key);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public V read(K key, Function<K, V> defaultValue) {
        if (index.has(key)) {
            try {
                return index.get(key);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        return defaultValue.apply(key);
    }

    public Index<K, V> getIndex() {
        return index;
    }

    public V save(Identifier<K> key, V value) {
        return save(key.getValue(), value);
    }

    public void delete(Identifier<K> key) {
        delete(key.getValue());
    }

    public V read(Identifier<K> key, Function<K, V> defaultValue) {
        return read(key.getValue(), defaultValue);
    }

}
