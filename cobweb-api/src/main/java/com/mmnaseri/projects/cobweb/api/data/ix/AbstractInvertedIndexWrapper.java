package com.mmnaseri.projects.cobweb.api.data.ix;

import com.mmnaseri.projects.cobweb.api.common.Bag;
import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/17, 8:50 AM)
 */
public abstract class AbstractInvertedIndexWrapper<K extends Serializable & Comparable<K>, V extends Serializable & Bag<K>> extends IndexWrapper<K, V> {

    AbstractInvertedIndexWrapper(Index<K, V> index) {
        super(index);
    }

    public void delete(Persistent<K> key) {
        delete(key.getId());
    }

    void cleanUp(K key, V values) {
        if (values.isEmpty()) {
            delete(key);
        } else {
            save(key, values);
        }
    }

    public abstract K remove(K key, K reverse);

    public abstract V read(K key);

    public abstract V read(Identifier<K> key);

    public void remove(Persistent<K> key, Persistent<K> reverse) {
        remove(key.getId().getValue(), reverse.getId().getValue());
    }

    private void removeFromInvertedIndex(K key, AbstractInvertedIndexWrapper<K, ? extends Bag<K>> index) {
        read(key).values().forEach(items -> index.remove(items, key));
    }

    public void removeFromInvertedIndex(Persistent<K> persistent, AbstractInvertedIndexWrapper<K, ? extends Bag<K>> index) {
        removeFromInvertedIndex(persistent.getId().getValue(), index);
    }

}
