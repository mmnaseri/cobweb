package com.mmnaseri.projects.cobweb.api.data.ix;

import com.mmnaseri.projects.cobweb.api.common.SerializableSet;
import com.mmnaseri.projects.cobweb.api.data.Index;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/17, 1:43 PM)
 */
class MockInvertedIndexWrapper<K extends Serializable & Comparable<K>> extends AbstractInvertedIndexWrapper<K, SerializableSet<K>> {

    private final List<K> expectedRemoveKeys;
    private final List<K> expectedRemoveReverses;
    private final List<K> expectedReads;
    private final List<K> returnRemove;
    private final List<Function<K, SerializableSet<K>>> defaultProviders;
    
    MockInvertedIndexWrapper(Index<K, SerializableSet<K>> index) {
        super(index);
        expectedRemoveKeys = new LinkedList<>();
        expectedRemoveReverses = new LinkedList<>();
        returnRemove = new LinkedList<>();
        expectedReads = new LinkedList<>();
        defaultProviders = new LinkedList<>();
    }

    @Override
    public K remove(K key, K reverse) {
        if (expectedRemoveKeys.isEmpty() || expectedRemoveReverses.isEmpty()) {
            throw new AssertionError();
        }
        if (!expectedRemoveKeys.remove(key)|| !expectedRemoveReverses.remove(reverse)) {
            throw new AssertionError();
        }
        return returnRemove.remove(0);
    }

    @Override
    public SerializableSet<K> read(K key) {
        if (expectedReads.isEmpty() || defaultProviders.isEmpty() || !expectedReads.remove(0).equals(key)) {
            throw new AssertionError();
        }
        return read(key, defaultProviders.remove(0));
    }
    
    public void expectRemove(K key, K reverse, K returnValue) {
        expectedRemoveKeys.add(key);
        expectedRemoveReverses.add(key);
        returnRemove.add(key);
    }
    
    public void expectRead(K key, Function<K, SerializableSet<K>> defaultProvider) {
        expectedReads.add(key);
        defaultProviders.add(defaultProvider);
    }

}
