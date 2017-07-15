package com.mmnaseri.projects.cobweb.api.data.ix;

import com.mmnaseri.projects.cobweb.api.data.impl.InMemoryIndex;
import com.mmnaseri.projects.cobweb.api.data.model.SerializableDocument;
import com.mmnaseri.projects.cobweb.domain.id.IdentifierFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/17, 1:17 PM)
 */
public class IndexWrapperTest {

    private InMemoryIndex<UUID, SerializableDocument> index;
    private IndexWrapper<UUID, SerializableDocument> wrapper;
    private IdentifierFactory<UUID> identifierFactory;

    @BeforeMethod
    public void setUp() throws Exception {
        index = new InMemoryIndex<>();
        wrapper = new IndexWrapper<>(index);
        identifierFactory = IdentifierFactory.UUID_IDENTIFIER_FACTORY;
    }

    @Test
    public void testThatItHoldsTheIndexAsIs() throws Exception {
        assertThat(wrapper.getIndex(), is(index));
    }

    @Test
    public void testThatItInsertsValues() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument document = new SerializableDocument(key);
        assertThat(index.has(key), is(false));
        assertThat(index.count(), is(0L));
        wrapper.save(identifierFactory.getIdentifier(key), document);
        assertThat(index.has(key), is(true));
        assertThat(index.count(), is(1L));
        assertThat(index.get(key), is(document));
    }

    @Test
    public void testThatItUpdatesValues() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument document = new SerializableDocument(key);
        wrapper.save(identifierFactory.getIdentifier(key), document);
        assertThat(index.has(key), is(true));
        assertThat(index.count(), is(1L));
        assertThat(index.get(key), is(document));
        final SerializableDocument newDocument = new SerializableDocument(key);
        wrapper.save(identifierFactory.getIdentifier(key), newDocument);
        assertThat(index.has(key), is(true));
        assertThat(index.count(), is(1L));
        assertThat(index.get(key), is(newDocument));
    }

    @Test
    public void testThatItDeletesValues() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument document = new SerializableDocument(key);
        index.store(key, document);
        wrapper.delete(identifierFactory.getIdentifier(key));
        assertThat(index.has(key), is(false));
        assertThat(index.count(), is(0L));
    }

    @Test
    public void testThatItReadsValuesIfTheyExist() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument document = new SerializableDocument(key);
        index.store(key, document);
        final SerializableDocument read = wrapper.read(identifierFactory.getIdentifier(key), null);
        assertThat(read, is(document));
    }

    @Test
    public void testThatItUsesTheDefaultValueIfValuesDoNotExist() throws Exception {
        final UUID key = UUID.randomUUID();
        final SerializableDocument document = new SerializableDocument(key);
        final SerializableDocument read = wrapper.read(identifierFactory.getIdentifier(key), uuid -> document);
        assertThat(read, is(document));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testThatItComplainsIfValueDoesNotExistAndThereIsNoDefault() throws Exception {
        wrapper.read(identifierFactory.getIdentifier(UUID.randomUUID()), null);
    }

}