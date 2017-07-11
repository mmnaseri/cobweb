package com.mmnaseri.projects.cobweb.api.data.impl.nio.impl;

import com.google.common.jimfs.Jimfs;
import com.mmnaseri.projects.cobweb.api.common.FileSystemUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ObjectInputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:09 PM)
 */
public class SerializableObjectWriterTest {

    private FileSystem fileSystem;

    @BeforeMethod
    public void setUp() throws Exception {
        fileSystem = Jimfs.newFileSystem();
        final Path path = FileSystemUtils.getAbsolutePath(fileSystem, "tmp");
        Files.createDirectory(path);
    }

    @Test
    public void testWritingObject() throws Exception {
        final SerializableObjectWriter writer = new SerializableObjectWriter();
        final String written = "This is a test";
        final Path path = FileSystemUtils.getAbsolutePath(fileSystem, "tmp", "file.bin");
        assertThat(Files.exists(path), is(false));
        writer.write(path, written);
        assertThat(Files.exists(path), is(true));
        final Object read;
        try (
                final ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(path, StandardOpenOption.READ))
        ) {
            read = stream.readObject();
        }
        assertThat(read, is(written));
    }

}