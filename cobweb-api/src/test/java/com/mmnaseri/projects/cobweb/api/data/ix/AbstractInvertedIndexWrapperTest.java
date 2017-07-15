package com.mmnaseri.projects.cobweb.api.data.ix;

import com.google.common.collect.Sets;
import com.mmnaseri.projects.cobweb.api.common.SerializableSet;
import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.api.data.impl.InMemoryIndex;
import com.mmnaseri.projects.cobweb.api.data.model.SerializableDocument;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/17, 1:33 PM)
 */
public class AbstractInvertedIndexWrapperTest {

    private Index<UUID, SerializableSet<UUID>> forward;
    private Index<UUID, SerializableSet<UUID>> reverse;
    private MockInvertedIndexWrapper<UUID> wrapper;
    private InvertedIndexWrapper<UUID> reverseWrapper;

    @BeforeMethod
    public void setUp() throws Exception {
        forward = new InMemoryIndex<>();
        reverse = new InMemoryIndex<>();
        wrapper = new MockInvertedIndexWrapper<>(forward);
        reverseWrapper = new InvertedIndexWrapper<>(reverse);
    }

    @Test
    public void testThatItReadsEmptyBagForNewKey() throws Exception {
        final UUID key = UUID.randomUUID();
        wrapper.expectRead(key, uuid -> new SerializableSet<>());
        final SerializableSet<UUID> set = wrapper.read(new SerializableDocument(key));
        assertThat(set, is(notNullValue()));
        assertThat(set, is(empty()));
    }

    @Test
    public void testThatItemCanBeRemovedFromInvertedIndex() throws Exception {
        final UUID first = UUID.randomUUID();
        final UUID second = UUID.randomUUID();
        final UUID third = UUID.randomUUID();
        final UUID key = UUID.randomUUID();
        reverse.store(first, new SerializableSet<>(Sets.newHashSet(key)));
        reverse.store(second, new SerializableSet<>(Sets.newHashSet(key)));
        reverse.store(third, new SerializableSet<>(Sets.newHashSet(key)));
        forward.store(key, new SerializableSet<>(Sets.newHashSet(first, second)));
        wrapper.expectRead(key, SerializableSet::create);
        wrapper.removeFromInvertedIndex(new SerializableDocument(key), reverseWrapper);
    }

}