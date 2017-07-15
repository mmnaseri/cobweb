package com.mmnaseri.projects.cobweb.api.data.ix;

import com.mmnaseri.projects.cobweb.api.common.SerializableMap;
import com.mmnaseri.projects.cobweb.api.data.impl.InMemoryIndex;
import com.mmnaseri.projects.cobweb.api.data.model.SerializableDocument;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/17, 2:49 PM)
 */
public class NamedInvertedIndexWrapperTest {

    private InMemoryIndex<UUID, SerializableMap<String, UUID>> index;
    private NamedInvertedIndexWrapper<UUID> wrapper;

    @BeforeMethod
    public void setUp() throws Exception {
        index = new InMemoryIndex<>();
        wrapper = new NamedInvertedIndexWrapper<>(index);
    }

    @Test
    public void testThatReadReturnsAnEmptyMapForNewKeys() throws Exception {
        assertThat(wrapper.read(UUID.randomUUID()), is(notNullValue()));
        assertThat(wrapper.read(UUID.randomUUID()).size(), is(0));
    }

    @Test
    public void testThatReadReturnsValuesForExistingKeys() throws Exception {
        final SerializableMap<String, UUID> map = new SerializableMap<>();
        map.put("1", UUID.randomUUID());
        map.put("2", UUID.randomUUID());
        final UUID key = UUID.randomUUID();
        index.store(key, map);
        assertThat(wrapper.read(key), is(notNullValue()));
        assertThat(wrapper.read(key), is(map));
    }

    @Test
    public void testThatIndividualItemsCanBeInserted() throws Exception {
        final SerializableDocument document = new SerializableDocument(UUID.randomUUID());
        final String name = "1";
        final SerializableDocument reverse = new SerializableDocument(UUID.randomUUID());
        wrapper.put(document, name, reverse);
        assertThat(index.has(document.getId().getValue()), is(true));
        final SerializableMap<String, UUID> read = index.get(document.getId().getValue());
        assertThat(read, is(notNullValue()));
        assertThat(read.size(), is(1));
        assertThat(read, hasKey(name));
        assertThat(read.get(name), is(reverse.getId().getValue()));
    }

}