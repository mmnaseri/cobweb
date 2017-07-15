package com.mmnaseri.projects.cobweb.api.data.ix;

import com.google.common.collect.Sets;
import com.mmnaseri.projects.cobweb.api.common.SerializableSet;
import com.mmnaseri.projects.cobweb.api.data.impl.InMemoryIndex;
import com.mmnaseri.projects.cobweb.api.data.model.SerializableDocument;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/17, 2:38 PM)
 */
public class InvertedIndexWrapperTest {

    private InMemoryIndex<UUID, SerializableSet<UUID>> forward;
    private InvertedIndexWrapper<UUID> wrapper;

    @BeforeMethod
    public void setUp() throws Exception {
        forward = new InMemoryIndex<>();
        wrapper = new InvertedIndexWrapper<>(forward);
    }

    @Test
    public void testThatItReturnsAnEmptyBagForNewValues() throws Exception {
        final SerializableSet<UUID> set = wrapper.read(new SerializableDocument(UUID.randomUUID()));
        assertThat(set, is(notNullValue()));
        assertThat(set, is(empty()));
    }

    @Test
    public void testThatItReturnsCorrectValuesForExistingKeys() throws Exception {
        final UUID key = UUID.randomUUID();
        final Set<UUID> values = Sets.newHashSet(UUID.randomUUID(), UUID.randomUUID());
        forward.store(key, new SerializableSet<>(values));
        final SerializableSet<UUID> read = wrapper.read(key);
        assertThat(read, is(notNullValue()));
        assertThat(read, is(not(empty())));
        assertThat(read.size(), is(values.size()));
        for (UUID uuid : read) {
            assertThat(values.contains(uuid), is(true));
        }
    }

    @Test
    public void testThatItCanStoreIndividualEntries() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument source = new SerializableDocument(key);
        final SerializableDocument first = new SerializableDocument(UUID.randomUUID());
        final SerializableDocument second = new SerializableDocument(UUID.randomUUID());
        wrapper.add(source, first);
        wrapper.add(source, second);
        assertThat(forward.has(key), is(true));
        assertThat(forward.get(key), hasSize(2));
        assertThat(forward.get(key), containsInAnyOrder(first.getId().getValue(), second.getId().getValue()));
    }

    @Test
    public void testThatItCanRemoveIndividualEntries() throws Exception {
        final UUID key = UUID.randomUUID();
        final UUID first = UUID.randomUUID();
        final UUID second = UUID.randomUUID();
        final UUID third = UUID.randomUUID();
        forward.store(key, new SerializableSet<>(Sets.newHashSet(first, second, third)));
        wrapper.remove(key, first);
        assertThat(forward.get(key), hasSize(2));
        assertThat(forward.get(key), containsInAnyOrder(second, third));
        wrapper.remove(key, second);
        assertThat(forward.get(key), hasSize(1));
        assertThat(forward.get(key), containsInAnyOrder(third));
    }

    @Test
    public void testThatItCleansUpAfterItself() throws Exception {
        final UUID key = UUID.randomUUID();
        final UUID first = UUID.randomUUID();
        forward.store(key, new SerializableSet<>(Sets.newHashSet(first)));
        assertThat(forward.has(key), is(true));
        assertThat(forward.get(key), hasSize(1));
        wrapper.remove(key, first);
        assertThat(forward.has(key), is(false));
    }

}