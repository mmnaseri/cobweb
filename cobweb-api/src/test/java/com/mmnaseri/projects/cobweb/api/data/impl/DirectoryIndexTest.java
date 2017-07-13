package com.mmnaseri.projects.cobweb.api.data.impl;

import com.google.common.jimfs.Jimfs;
import com.mmnaseri.projects.cobweb.api.common.FileSystemUtils;
import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.api.data.impl.io.impl.SerializableObjectInputOutputManager;
import com.mmnaseri.projects.cobweb.api.data.model.SerializableDocument;
import org.testng.annotations.Test;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static com.mmnaseri.projects.cobweb.api.data.impl.DirectoryIndexConfiguration.forIndexType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 5:26 PM)
 */
public class DirectoryIndexTest extends BaseIndexTest {

    private Path root;

    @Override
    protected Index<UUID, SerializableDocument> setUpIndex() throws Exception {
        final FileSystem fileSystem = Jimfs.newFileSystem();
        root = FileSystemUtils.getAbsolutePath(fileSystem, "data", "index");
        final DirectoryIndexFactory factory = new DirectoryIndexFactory();
        final DirectoryIndexConfiguration<UUID, SerializableDocument> manager = forIndexType(UUID.class, SerializableDocument.class)
                .andRootPath(root)
                .andIOManager(new SerializableObjectInputOutputManager<>())
                .andStringifier(new UUIDStringifier())
                .build();

        return factory.getInstance(manager);
    }

    @Test
    public void testInsertingCreatesANewFileWhenStoring() throws Exception {
        assertThat(Files.list(root).count(), is(0L));
        final UUID key = UUID.randomUUID();
        getIndex().store(key, new SerializableDocument(key));
        assertThat(Files.list(root).count(), is(1L));
    }

    @Test
    public void testUpdatingUsesTheSameFile() throws Exception {
        assertThat(Files.list(root).count(), is(0L));
        final UUID key = UUID.randomUUID();
        getIndex().store(key, new SerializableDocument(key));
        assertThat(Files.list(root).count(), is(1L));
        final Path path = Files.list(root).findFirst().orElse(null);
        getIndex().update(key, new SerializableDocument(key));
        assertThat(Files.list(root).count(), is(1L));
        assertThat(Files.list(root).findFirst().orElse(null), is(path));
    }

    @Test
    public void testDeletingEntryDeletesTheFile() throws Exception {
        assertThat(Files.list(root).count(), is(0L));
        final UUID key = UUID.randomUUID();
        getIndex().store(key, new SerializableDocument(key));
        assertThat(Files.list(root).count(), is(1L));
        getIndex().delete(key);
        assertThat(Files.list(root).count(), is(0L));
    }

}