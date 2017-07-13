package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.data.Dictionary;
import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.api.data.model.SerializableDocument;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 7:58 PM)
 */
public abstract class BaseIndexTest extends BaseDictionaryTest {

    private Index<UUID, SerializableDocument> index;

    @Override
    protected final Dictionary<UUID> setUpDictionary() throws Exception {
        index = setUpIndex();
        return index;
    }

    Index<UUID, SerializableDocument> getIndex() {
        return index;
    }

    protected abstract Index<UUID,SerializableDocument> setUpIndex() throws Exception;

    @Test
    public void testThatUpdatingNonExistentKeyDoesNothing() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument value = new SerializableDocument(key);
        assertThat(index.count(), is(0L));
        assertThat(index.update(key, value), is(false));
        assertThat(index.count(), is(0L));
    }

    @Test
    public void testThatInsertingValuesWorks() throws Exception {
        final UUID firstKey = UUID.randomUUID();
        final SerializableDocument firstValue = new SerializableDocument(firstKey);
        final UUID secondKey = UUID.randomUUID();
        final SerializableDocument secondValue = new SerializableDocument(secondKey);
        assertThat(index.count(), is(0L));
        index.store(firstKey, firstValue);
        assertThat(index.count(), is(1L));
        assertThat(index.has(firstKey), is(true));
        assertThat(index.get(firstKey), is(firstValue));
        index.store(secondKey, secondValue);
        assertThat(index.count(), is(2L));
        assertThat(index.has(secondKey), is(true));
        assertThat(index.get(secondKey), is(secondValue));
    }

    @Test
    public void testUpdatingAnExistingValueReplacesIt() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument value = new SerializableDocument(key);
        assertThat(index.count(), is(0L));
        index.store(key, value);
        assertThat(index.count(), is(1L));
        assertThat(index.get(key), is(value));
        final SerializableDocument newValue = new SerializableDocument(key);
        assertThat(index.update(key, newValue), is(true));
        assertThat(index.count(), is(1L));
        assertThat(index.get(key), is(newValue));
    }

    @Test
    public void testUpsertInsertsNonExistentKeys() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument value = new SerializableDocument(key);
        assertThat(index.count(), is(0L));
        assertThat(index.upsert(key, value), is(true));
        assertThat(index.count(), is(1L));
        assertThat(index.get(key), is(value));
    }

    @Test
    public void testUpsertUpdatesExistingKeys() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument value = new SerializableDocument(key);
        index.store(key, value);
        assertThat(index.count(), is(1L));
        final SerializableDocument newValue = new SerializableDocument(key);
        assertThat(index.upsert(key, newValue), is(true));
        assertThat(index.count(), is(1L));
        assertThat(index.get(key), is(newValue));
    }

    @Test
    public void testValuesAreKnown() throws Exception {
        final UUID firstKey = UUID.randomUUID();
        final SerializableDocument firstValue = new SerializableDocument(firstKey);
        final UUID secondKey = UUID.randomUUID();
        final SerializableDocument secondValue = new SerializableDocument(secondKey);
        index.store(firstKey, firstValue);
        index.store(secondKey, secondValue);
        final List<SerializableDocument> values = index.values();
        assertThat(values, containsInAnyOrder(firstValue, secondValue));
    }

}
