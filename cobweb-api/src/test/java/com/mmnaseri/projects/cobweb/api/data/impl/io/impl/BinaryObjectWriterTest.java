package com.mmnaseri.projects.cobweb.api.data.impl.io.impl;

import com.google.common.jimfs.Jimfs;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.FileSystem;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 5:32 PM)
 */
public class BinaryObjectWriterTest {

    private FileSystem fileSystem;

    @BeforeMethod
    public void setUp() throws Exception {
        fileSystem = Jimfs.newFileSystem();
    };

    @Test
    public void testWritingAFile() throws Exception {
        final Path path = fileSystem.getPath("tmp", "sample.bin");
        final File file = path.toFile();
        final BinaryObjectWriter writer = new BinaryObjectWriter();
        final String object = "This is a test";
        writer.write(file, object);
        final Object read;
        try (final ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
            read = stream.readObject();
        }
        assertThat(read, is(object));
    }

}