package com.mmnaseri.projects.cobweb.api.data.impl.io.impl;

import com.google.common.jimfs.Jimfs;
import com.mmnaseri.projects.cobweb.api.common.FileSystemUtils;
import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectWriter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 6:04 PM)
 */
public abstract class BaseObjectWriterTest {

    private FileSystem fileSystem;

    @BeforeMethod
    public void setUp() throws Exception {
        fileSystem = Jimfs.newFileSystem();
        final Path path = FileSystemUtils.getAbsolutePath(fileSystem, "tmp");
        Files.createDirectory(path);
    }

    @Test
    public void testWritingObject() throws Exception {
        final ObjectWriter writer = getObjectWriter();
        final String written = "This is a test";
        final Path path = FileSystemUtils.getAbsolutePath(fileSystem, "tmp", "file.bin");
        assertThat(Files.exists(path), is(false));
        writer.write(path, written);
        assertThat(Files.exists(path), is(true));
        final Object read = read(path);
        assertThat(read, is(written));
    }

    protected abstract ObjectWriter getObjectWriter();

    protected abstract Object read(Path path) throws IOException, ClassNotFoundException;

}
