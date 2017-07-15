package com.mmnaseri.projects.cobweb.api.data.ix;

import com.mmnaseri.projects.cobweb.api.common.SerializableSet;
import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/17, 12:57 AM)
 */
public class InvertedIndexWrapper<K extends Serializable & Comparable<K>> extends AbstractInvertedIndexWrapper<K, SerializableSet<K>> {

    public InvertedIndexWrapper(Index<K, SerializableSet<K>> index) {
        super(index);
    }

    public SerializableSet<K> read(K key) {
        return super.read(key, SerializableSet::create);
    }

    public K remove(K key, K reverse) {
        final SerializableSet<K> values = read(key);
        values.remove(reverse);
        cleanUp(key, values);
        return reverse;
    }

    public void add(K key, K reverse) {
        final SerializableSet<K> values = read(key);
        values.add(reverse);
        save(key, values);
    }

    public void add(Identifier<K> key, Identifier<K> reverse) {
        add(key.getValue(), reverse.getValue());
    }

    public void add(Persistent<K> source, Persistent<K> target) {
        add(source.getId(), target.getId());
    }

}
