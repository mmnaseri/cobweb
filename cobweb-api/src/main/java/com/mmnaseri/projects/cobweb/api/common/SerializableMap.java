package com.mmnaseri.projects.cobweb.api.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 8:51 PM)
 */
public class SerializableMap<K extends Serializable, V extends Serializable> implements Map<K, V>, Serializable {

    private static final long serialVersionUID = -3304814197248864075L;
    private Map<K, V> map;

    public SerializableMap() {
        this(new HashMap<>());
    }

    public SerializableMap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerializableMap<?, ?> that = (SerializableMap<?, ?>) o;
        return map.equals(that.map);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public String toString() {
        return map.toString();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        final HashMap<K, V> clone = new HashMap<>(map);
        out.writeObject(clone);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        //noinspection unchecked
        final Map<K, V> data = (Map<K, V>) in.readObject();
        map = new HashMap<>(data);
    }

}
