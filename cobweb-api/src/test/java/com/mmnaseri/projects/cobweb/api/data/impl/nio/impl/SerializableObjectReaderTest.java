package com.mmnaseri.projects.cobweb.api.data.impl.nio.impl;

import com.google.common.jimfs.Jimfs;
import com.mmnaseri.projects.cobweb.api.common.FileSystemUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ObjectOutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:32 PM)
 */
public class SerializableObjectReaderTest {

    private FileSystem fileSystem;

    @BeforeMethod
    public void setUp() throws Exception {
        fileSystem = Jimfs.newFileSystem();
        final Path path = FileSystemUtils.getAbsolutePath(fileSystem, "tmp");
        Files.createDirectory(path);
    }

    @Test
    public void testReadingAnObject() throws Exception {
        final String written = "This is a test";
        final Path path = FileSystemUtils.getAbsolutePath(fileSystem, "tmp", "file.bin");
        final ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(path));
        stream.writeObject(written);
        final SerializableObjectReader<String> reader = new SerializableObjectReader<>();
        final String read = reader.read(path, String.class);
        assertThat(read, is(written));
    }

}