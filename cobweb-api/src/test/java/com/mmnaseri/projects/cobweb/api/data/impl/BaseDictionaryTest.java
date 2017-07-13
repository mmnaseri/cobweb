package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.data.Dictionary;
import com.mmnaseri.projects.cobweb.api.data.model.SerializableDocument;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 5:56 PM)
 */
public abstract class BaseDictionaryTest {

    protected Dictionary<UUID> dictionary;

    @BeforeMethod
    public void setUp() throws Exception {
        dictionary = setUpDictionary();
    }

    Dictionary<UUID> getDictionary() {
        return dictionary;
    }

    protected abstract Dictionary<UUID> setUpDictionary() throws Exception;

    @Test
    public void testInitiallyEmpty() throws Exception {
        assertThat(dictionary.count(), is(0L));
    }

    @Test
    public void testInsertedValuesAreRemembered() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument value = new SerializableDocument(key);
        assertThat(dictionary.has(key), is(false));
        dictionary.store(key);
        assertThat(dictionary.has(key), is(true));
    }

    @Test
    public void testThatInsertingWorks() throws Exception {
        final UUID firstKey = UUID.randomUUID();
        final UUID secondKey = UUID.randomUUID();
        assertThat(dictionary.count(), is(0L));
        dictionary.store(firstKey);
        assertThat(dictionary.count(), is(1L));
        assertThat(dictionary.has(firstKey), is(true));
        dictionary.store(secondKey);
        assertThat(dictionary.count(), is(2L));
        assertThat(dictionary.has(secondKey), is(true));
    }

    @Test
    public void testThatInsertingDuplicatesDoesNothing() throws Exception {
        final UUID key = UUID.randomUUID();
        assertThat(dictionary.count(), is(0L));
        assertThat(dictionary.store(key), is(true));
        assertThat(dictionary.count(), is(1L));
        for (int i = 0; i < 10; i++) {
            assertThat(dictionary.store(key), is(false));
            assertThat(dictionary.count(), is(1L));
        }
    }

    @Test
    public void testThatDeletingWorks() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument value = new SerializableDocument(key);
        assertThat(dictionary.count(), is(0L));
        dictionary.store(key);
        assertThat(dictionary.count(), is(1L));
        assertThat(dictionary.delete(key), is(true));
        assertThat(dictionary.count(), is(0L));
    }

    @Test
    public void testThatDeletingNonExistentKeysDoesNothing() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument value = new SerializableDocument(key);
        assertThat(dictionary.count(), is(0L));
        dictionary.store(key);
        assertThat(dictionary.count(), is(1L));
        assertThat(dictionary.delete(UUID.randomUUID()), is(false));
        assertThat(dictionary.count(), is(1L));
    }

    @Test
    public void testTruncatingIndexZeroesIt() throws Exception {
        final long size = 10L;
        for (long i = 0L; i < size; i++) {
            final UUID key = UUID.randomUUID();
            dictionary.store(key);
        }
        assertThat(dictionary.count(), is(size));
        dictionary.truncate();
        assertThat(dictionary.count(), is(0L));
    }

    @Test
    public void testKeysAreKnown() throws Exception {
        final UUID firstKey = UUID.randomUUID();
        final UUID secondKey = UUID.randomUUID();
        dictionary.store(firstKey);
        dictionary.store(secondKey);
        final List<UUID> keys = dictionary.keys();
        assertThat(keys, containsInAnyOrder(firstKey, secondKey));
    }
}
