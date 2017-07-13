package com.mmnaseri.projects.cobweb.api.data.ix;

import com.mmnaseri.projects.cobweb.api.common.SerializableMap;
import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/17, 1:02 AM)
 */
public class NamedInvertedIndexWrapper<K extends Serializable & Comparable<K>> extends AbstractInvertedIndexWrapper<K, SerializableMap<String, K>> {

    public NamedInvertedIndexWrapper(Index<K, SerializableMap<String, K>> index) {
        super(index);
    }

    public SerializableMap<String, K> read(K key) {
        return super.read(key, k -> new SerializableMap<>());
    }

    public SerializableMap<String, K> read(Identifier<K> key) {
        return super.read(key, k -> new SerializableMap<>());
    }

    public void put(Persistent<K> key, String name, Persistent<K> reverse) {
        final SerializableMap<String, K> values = read(key.getId().getValue());
        values.put(name, reverse.getId().getValue());
        save(key.getId().getValue(), values);
    }

    public K remove(K key, K reverse) {
        final SerializableMap<String, K> values = read(key);
        final Set<String> deleted = values.keySet().stream()
                .filter(name -> values.get(name).equals(reverse))
                .collect(Collectors.toSet());
        deleted.forEach(values::remove);
        cleanUp(key, values);
        return key;
    }

}
